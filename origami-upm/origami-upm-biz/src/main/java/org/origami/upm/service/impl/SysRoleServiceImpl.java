package org.origami.upm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.origami.upm.api.entity.SysRole;
import org.origami.upm.manager.UpmManager;
import org.origami.upm.mapper.SysRoleMapper;
import org.origami.upm.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author origami
 * @date 2022/1/13 22:05
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements
        SysRoleService {

    private final UpmManager upmManager;

    @Override
    public List<SysRole> getListByUserId(Long userId) {
        return baseMapper.selectListByUserId(userId);
    }

    @Override
    public Map<String, Set<String>> getPermissionRoleMap() {
        return upmManager.getPermissionRoleMap();
    }
}
