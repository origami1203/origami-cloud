package org.origami.upm.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.origami.upm.api.entity.SysLog;
import org.origami.upm.mapper.SysLogMapper;
import org.origami.upm.service.SysLogService;
import org.springframework.stereotype.Service;

/**
 * 实现类
 *
 * @author origami
 * @date 2021/12/31 12:08
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements
                                                                         SysLogService {

}
