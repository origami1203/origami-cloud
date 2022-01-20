package org.origami.upm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.origami.upm.api.entity.SysPermission;
import org.origami.upm.mapper.SysPermissionMapper;
import org.origami.upm.service.SysPermissionService;
import org.springframework.stereotype.Service;

/**
 * @author origami
 * @date 2022/1/13 22:48
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission>
        implements SysPermissionService {
}
