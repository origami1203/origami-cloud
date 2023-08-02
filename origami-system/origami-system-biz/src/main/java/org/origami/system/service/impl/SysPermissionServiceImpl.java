package org.origami.system.service.impl;

import java.util.List;
import java.util.Set;

import org.origami.boot.mybatisplus.service.impl.ServiceImpl;
import org.origami.common.core.utils.Assert;
import org.origami.common.core.utils.BeanUtils;
import org.origami.common.core.utils.TreeUtil.Node;
import org.origami.system.dto.CreatePermissionRequest;
import org.origami.system.dto.LoginUser;
import org.origami.system.dto.SysPermissionRoleDTO;
import org.origami.system.dto.UpdatePermissionRequest;
import org.origami.system.entity.SysPermission;
import org.origami.system.entity.SysRoleDO;
import org.origami.system.manager.SysPermissionManager;
import org.origami.system.mapper.SysPermissionMapper;
import org.origami.system.mapper.SysRolePermissionMapper;
import org.origami.system.service.SysPermissionService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import lombok.RequiredArgsConstructor;

/**
 * @author origami
 * @date 2022/1/13 22:48
 */
@Service
@RequiredArgsConstructor
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission>
    implements SysPermissionService {

    private final SysRolePermissionMapper sysRolePermissionMapper;
    private final SysPermissionManager sysPermissionManager;

    @Override
    public SysPermission save(CreatePermissionRequest createPermissionRequest) {
        SysPermission entity = new SysPermission();
        BeanUtils.copyProperties(createPermissionRequest, entity);
        check(entity);
        super.save(entity);
        return entity;
    }

    @Override
    public SysPermission update(UpdatePermissionRequest updateRequest) {
        SysPermission entity = new SysPermission();
        BeanUtils.copyProperties(updateRequest, entity);
        check(entity);
        updateById(entity);
        return entity;
    }

    @Override
    public boolean removeById(Long id) {
        Set<Long> roleIds = sysRolePermissionMapper.selectRolesByPermissionId(id);
        Assert.isEmpty(roleIds, "存在角色绑定此权限.无法删除");
        return super.removeById(id);
    }

    @Override
    public List<LoginUser> findUsersWithPermission(Long permissionId) {
        return null;
    }

    @Override
    public boolean assigningRolePermissions(Long roleId, List<Long> permsId) {
        return sysPermissionManager.assignPermsToRole(roleId, permsId);
    }

    @Override
    public List<SysRoleDO> findRolesWithPermission(Long permissionId) {
        return null;
    }

    @Override
    public void addOrgPermission(Long orgId, Long permissionId) {

    }

    @Override
    public void deleteOrgPermission(Long orgId, Long permissionId) {

    }

    @Override
    public List<SysPermission> getRolePerms(Long roleId) {
        return baseMapper.selectPermissionsByRoleId(roleId);
    }

    @Override
    public List<SysPermissionRoleDTO> getPermissionRoles() {
        return sysPermissionManager.getPermissionRoles();
    }

    private void check(SysPermission sysPermission) {
        Wrapper<SysPermission> wrapper = new LambdaQueryWrapper<SysPermission>().or().eq(sysPermission.getUrl() != null,
            SysPermission::getUrl, sysPermission.getUrl());

        Long count = baseMapper.selectCount(wrapper);

        Assert.isTrue(count <= 0, "已存在相同的url权限");
    }

    @Override
    public List<Node<Long>> getUserMenus() {

        return null;
    }
}
