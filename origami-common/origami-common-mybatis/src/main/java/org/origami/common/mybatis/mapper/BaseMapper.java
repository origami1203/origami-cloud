package org.origami.common.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.origami.common.core.data.entity.BaseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 基于mp的BaseMapper进行拓展
 *
 * @author origami
 * @date 2022/11/5 14:33
 */
public interface BaseMapper<T extends BaseEntity> extends
                                                  com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {
    
    /**
     * 批量插入
     *
     * @return int
     */
    @Transactional(rollbackFor = Exception.class)
    int insertBatchSomeColumn(@Param("list") List<T> list);
    
}
