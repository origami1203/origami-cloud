package org.origami.common.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.origami.common.core.utils.UserContextUtil;

import java.time.LocalDateTime;

/**
 * @author origami1203
 * @date 2021/12/29 18:32
 * 配置用于自动填充{@link org.origami.common.core.base.BaseEntity}中的数据
 */
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("mybatis-plus自动插入字段...");
        
        LocalDateTime now = LocalDateTime.now();
        
        strictInsertFill(metaObject, "createDate", LocalDateTime.class, now);
        strictInsertFill(metaObject, "updateDate", LocalDateTime.class, now);
        strictInsertFill(metaObject, "createBy", String.class, UserContextUtil.getUsername());
        strictInsertFill(metaObject, "updateBy", String.class, UserContextUtil.getUsername());
    }
    
    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("mybatis-plus自动更新字段开始...");
        
        strictInsertFill(metaObject, "updateDate", LocalDateTime.class, LocalDateTime.now());
        strictInsertFill(metaObject, "updateBy", String.class, UserContextUtil.getUsername());
    }
    
}
