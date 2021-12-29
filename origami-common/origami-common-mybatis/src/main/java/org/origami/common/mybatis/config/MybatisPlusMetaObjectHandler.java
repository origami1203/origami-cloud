package org.origami.common.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author origami1203
 * @date 2021/12/29 18:32
 * 配置用于自动填充{@link org.origami.common.mybatis.base.BaseEntity}中的数据
 */
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("mybatis-plus自动插入字段...");
        
        LocalDateTime now = LocalDateTime.now();
        
        strictInsertFill(metaObject, "createDate", LocalDateTime.class, now);
        strictInsertFill(metaObject, "updateDate", LocalDateTime.class, now);
        strictInsertFill(metaObject, "createBy", String.class, getUserName());
        strictInsertFill(metaObject, "updateBy", String.class, getUserName());
    }
    
    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("mybatis-plus自动填充开始...");
        
        strictInsertFill(metaObject, "updateDate", LocalDateTime.class, LocalDateTime.now());
        strictInsertFill(metaObject, "updateBy", String.class, getUserName());
    }
    
    /**
     * 获取当前线程已认证用户的名字
     *
     * @return 用户名，未认证返回null
     */
    private String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.nonNull(authentication)) {
            return authentication.getName();
        }
        return null;
    }
}
