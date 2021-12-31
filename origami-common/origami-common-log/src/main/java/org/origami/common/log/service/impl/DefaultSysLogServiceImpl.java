package org.origami.common.log.service.impl;

import org.origami.common.log.service.SysLogService;
import org.origami.upm.api.entity.SysLog;

/**
 * 默认实现类
 *
 * @author origami
 * @date 2022/1/1 3:57
 */
public class DefaultSysLogServiceImpl implements SysLogService {
    /**
     * 默认不做处理，可自定义实现
     * @param sysLog 日志
     */
    @Override
    public void handle(SysLog sysLog) {
       // 空处理
    }
}
