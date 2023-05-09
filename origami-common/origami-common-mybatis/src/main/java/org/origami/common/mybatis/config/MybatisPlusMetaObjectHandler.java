package org.origami.common.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.origami.common.core.utils.UserContextHolder;

import java.time.LocalDateTime;

/**
 * 配置用于自动填充{@link org.origami.common.core.data.entity.BaseEntity}中的数据
 *
 * @author origami1203
 * @date 2021/12/29 18:32
 */
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("mybatis-plus自动插入字段...");
        
        LocalDateTime now = LocalDateTime.now();
        // @formatter:off
        strictInsertFill(metaObject, "createDate", LocalDateTime.class, now);
        strictInsertFill(metaObject, "updateDate", LocalDateTime.class, now);
        strictInsertFill(metaObject, "createBy", String.class, UserContextHolder.getUsername());
        strictInsertFill(metaObject, "updateBy", String.class, UserContextHolder.getUsername());
        strictInsertFill(metaObject, "deleted", Boolean.class, Boolean.FALSE);
        strictInsertFill(metaObject, "version", Long.class, 0L);
        // @formatter:on
    }
    
    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("mybatis-plus自动更新字段开始...");
        
        strictUpdateFill(metaObject, "updateDate", LocalDateTime.class, LocalDateTime.now());
        strictUpdateFill(metaObject, "updateBy", String.class, UserContextHolder.getUsername());
    }
    
}
