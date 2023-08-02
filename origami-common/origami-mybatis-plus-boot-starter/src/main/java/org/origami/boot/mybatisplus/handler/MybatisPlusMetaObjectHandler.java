package org.origami.boot.mybatisplus.handler;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.origami.common.core.utils.CurrentUserHolder;
import org.origami.boot.mybatisplus.entity.BaseEntity;
import org.origami.boot.mybatisplus.entity.BaseEntity.Fields;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 配置用于自动填充{@link BaseEntity}中的数据
 *
 * @author origami1203
 * @date 2021/12/29 18:32
 */
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("mybatis-plus添加自动填充字段");

        LocalDateTime now = LocalDateTime.now();
        // @formatter:off
        strictInsertFill(metaObject, Fields.createDate, LocalDateTime.class, now);
        strictInsertFill(metaObject, Fields.updateDate, LocalDateTime.class, now);
        strictInsertFill(metaObject, Fields.createBy, String.class, CurrentUserHolder.getUsername());
        strictInsertFill(metaObject, Fields.updateBy, String.class, CurrentUserHolder.getUsername());
        strictInsertFill(metaObject, Fields.deleted, Boolean.class, Boolean.FALSE);
        strictInsertFill(metaObject, Fields.version, Long.class, 0L);
        // @formatter:on
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("mybatis-plus更新填充字段");

        strictUpdateFill(metaObject, Fields.updateDate, LocalDateTime.class, LocalDateTime.now());
        strictUpdateFill(metaObject, Fields.updateBy, String.class, CurrentUserHolder.getUsername());
    }

}
