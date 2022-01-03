package org.origami.common.jpa.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.resource.jdbc.spi.StatementInspector;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将jpa的sql格式化为可读
 * yml文件spring.jpa.properties.hibernate.session_factory.statement_inspector配置全路径类名
 *
 * @author origami
 * @date 2022/1/3 8:20
 */
@Slf4j
public class JpaSqlInterceptor implements StatementInspector {
    
    private static final String TABLE_NAME_FINDER = "([a-z]+[\\d]+(_)(\\.)[a-z]+)";
    
    private static final String AS_FINDER = "((as)(\\s)[a-z]+([\\d|a-z]+(_)+)+)";
    
    private static final String SQL_START = "select";
    
    private static final Integer SUB_TABLE_START = 0;
    
    private static final Integer SUB_TABLE_END = 2;
    
    @Override
    public String inspect(String sql) {
        String lowerSql = sql.toLowerCase();
        if (!lowerSql.startsWith(SQL_START)) {
            return sql;
        }
        Pattern table = Pattern.compile(TABLE_NAME_FINDER);
        Matcher tableMatcher = table.matcher(lowerSql);
        List<String> aliasTableNames = new ArrayList<>();
        while (tableMatcher.find()) {
            String s = tableMatcher.group();
            String[] split = s.split("\\.");
            aliasTableNames.add(split[0]);
        }
        
        formatSql(lowerSql, aliasTableNames);
        
        return sql;
    }
    
    /**
     * 保留别名的SQL，可以运行
     */
    private void formatSql(String lowerSql, List<String> aliasTableNames) {
        for (String aliasTableName : aliasTableNames) {
            lowerSql = lowerSql.replace(aliasTableName,
                                        aliasTableName.substring(SUB_TABLE_START,
                                                                 SUB_TABLE_END));
        }
        Pattern as = Pattern.compile(AS_FINDER);
        Matcher asMatcher = as.matcher(lowerSql);
        while (asMatcher.find()) {
            String group = asMatcher.group();
            lowerSql = lowerSql.replace(group, "");
        }
        log.debug("自定义sql显示格式:{}", lowerSql);
    }
    
}
