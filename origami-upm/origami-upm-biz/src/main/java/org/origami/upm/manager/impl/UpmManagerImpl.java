package org.origami.upm.manager.impl;

import lombok.RequiredArgsConstructor;
import org.origami.upm.api.entity.SysPermission;
import org.origami.upm.api.entity.SysRole;
import org.origami.upm.manager.UpmManager;
import org.origami.upm.mapper.SysPermissionMapper;
import org.origami.upm.mapper.SysRoleMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-03-28 13:17
 */
@Service
@RequiredArgsConstructor
public class UpmManagerImpl implements UpmManager {

    private final SysRoleMapper sysRoleMapper;
    private final SysPermissionMapper sysPermissionMapper;

    // todo 缓存在manager层做
    @Override
    public Map<String, Set<String>> getPermissionRoleMap() {
        Map<String, Set<String>> result = new HashMap<>();

        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        List<SysPermission> permissions = sysPermissionMapper.selectList(null);

        permissions.forEach(it -> {
            Set<String> roles = sysRoleMapper.selectRolesByPermission(it.getPerms()).stream().map(SysRole::getRoleCode).collect(Collectors.toSet());
            result.put(it.getPerms(), roles);
        });

        return result;
    }
}
