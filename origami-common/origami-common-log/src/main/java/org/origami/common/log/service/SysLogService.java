package org.origami.common.log.service;

import org.origami.upm.api.entity.SysLog;

/**
 * 子类实现后自定义日志的处理方式
 *
 * @author origami
 * @date 2022/1/1 3:51
 */
public interface SysLogService {
    /**
     * 子类实现后，可将日志存入数据库，es等
     *
     * @param sysLog 日志
     */
    void handle(SysLog sysLog);
}
