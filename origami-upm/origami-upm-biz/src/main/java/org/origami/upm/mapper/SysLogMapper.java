package org.origami.upm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.origami.upm.api.entity.SysLog;

/**
 * 系统操作日志mapper
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2021-12-31 11:28
 */
@Mapper
public interface SysLogMapper extends BaseMapper<SysLog> {
}
