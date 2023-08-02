package org.origami.boot.mybatisplus.mapper;

import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.apache.ibatis.annotations.Param;
import org.origami.boot.mybatisplus.entity.BaseEntity;

import java.util.List;

/**
 * 基于mp的BaseMapper进行拓展
 *
 * @author origami
 * @date 2022/11/5 14:33
 */
public interface BaseMapper<T extends BaseEntity> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    /**
     * 批量插入
     *
     * @param list 列表
     * @return 影响的条数
     */
    default boolean insertBatch(@Param("list") List<T> list) {
        return Db.saveBatch(list);
    }

}
