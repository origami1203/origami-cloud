package org.origami.system.service.impl;

import org.origami.boot.mybatisplus.service.impl.ServiceImpl;
import org.origami.system.entity.SysLogDO;
import org.origami.system.mapper.SysLogMapper;
import org.origami.system.service.SysLogService;
import org.springframework.stereotype.Service;

/**
 * 实现类
 *
 * @author origami
 * @date 2021/12/31 12:08
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLogDO> implements SysLogService {
}
