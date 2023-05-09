package org.origami.upm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.origami.common.core.exception.IllegalParamException;
import org.origami.common.core.exception.NotFoundException;
import org.origami.upm.api.dto.SysRolePermissionDTO;
import org.origami.upm.api.entity.SysPermission;
import org.origami.upm.api.entity.SysRoleDO;
import org.origami.upm.api.entity.SysRolePermission;
import org.origami.upm.manager.SysRoleManager;
import org.origami.upm.mapper.SysPermissionMapper;
import org.origami.upm.mapper.SysRoleMapper;
import org.origami.upm.mapper.SysRolePermissionMapper;
import org.origami.upm.service.SysRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author origami
 * @date 2022/1/13 22:05
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleDO> implements
                                                                            SysRoleService {
    private final SysRoleManager sysRoleManager;
    private final SysPermissionMapper sysPermissionMapper;
    private final SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public List<SysRoleDO> getListByUserId(Long userId) {
        return baseMapper.selectListByUserId(userId);
    }

    @Override
    public Map<String, Set<String>> getPermissionRoleMap() {
        return sysRoleManager.getPermissionRoleMap();
    }

    @Override
    public boolean assignRolePermission(SysRolePermissionDTO dto) {
        List<Long> permIds = dto.getPermIds();

        SysRoleDO sysRole = Optional.ofNullable(baseMapper.selectById(dto.getRoleId()))
                                  .orElseThrow(NotFoundException::new);

        if (permIds.isEmpty()) {
            sysRolePermissionMapper.deleteByRoleId(dto.getRoleId());
            return true;
        }

        List<SysPermission> sysPermissions = sysPermissionMapper.selectBatchIds(permIds);

        if (permIds.size() != sysPermissions.size()) {
            throw new IllegalParamException("需要分配的权限id中有未找到的权限");
        }

        List<SysRolePermission> sysRolePermissions = sysPermissions.stream().map(it -> {
            SysRolePermission sysRolePermission = new SysRolePermission();
            return sysRolePermission.setPermissionId(it.getId()).setRoleId(dto.getRoleId());
        }).collect(Collectors.toList());

        sysRolePermissionMapper.insertBatchSomeColumn(sysRolePermissions);

        return true;

    }
}
