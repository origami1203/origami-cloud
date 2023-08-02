package org.origami.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.origami.boot.mybatisplus.mapper.BaseMapper;
import org.origami.system.entity.SysLogDO;

/**
 * 系统操作日志mapper
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2021-12-31 11:28
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLogDO> {
}
