package org.origami.system.service;

import java.util.List;
import org.origami.boot.mybatisplus.service.BaseService;
import org.origami.common.core.utils.TreeUtil.Node;
import org.origami.system.dto.LoginUser;
import org.origami.system.dto.CreatePermissionRequest;
import org.origami.system.dto.SysPermissionRoleDTO;
import org.origami.system.dto.UpdatePermissionRequest;
import org.origami.system.entity.SysPermission;
import org.origami.system.entity.SysRoleDO;

/**
 * @author origami
 * @date 2022/1/13 22:48
 */
public interface SysPermissionService extends BaseService<SysPermission> {

    /**
     * 保存
     *
     * @param createPermissionRequest 许可dto
     * @return {@code SysPermission}
     */
    SysPermission save(CreatePermissionRequest createPermissionRequest);

    /**
     * 更新
     *
     * @param updateRequest 许可dto
     * @return {@code SysPermission}
     */
    SysPermission update(UpdatePermissionRequest updateRequest);

    /**
     * 获取角色权限
     *
     * @param roleId 角色id
     * @return {@code List<SysPermission>}
     */
    List<SysPermission> getRolePerms(Long roleId);

    /**
     * 获取权限及其所需要的角色
     *
     * @return {@code List<SysPermissionRoleDTO>}
     */
    List<SysPermissionRoleDTO> getPermissionRoles();

    /**
     * 查询拥有指定权限的用户
     *
     * @param permissionId 特权id
     * @return {@code List<LoginUser>}
     */
    List<LoginUser> findUsersWithPermission(Long permissionId);

    /**
     * 给角色添加权限
     *
     * @param roleId 角色id
     * @param permsId 权限id
     * @return 是否成功
     */
    boolean assigningRolePermissions(Long roleId, List<Long> permsId);

    /**
     * 查询角色权限
     *
     * @param permissionId 权限id
     * @return {@code List<SysRoleDO>}
     */
    List<SysRoleDO> findRolesWithPermission(Long permissionId);

    /**
     * 添加org许可
     *
     * @param permissionId 权限id
     * @param orgId org id
     */
    void addOrgPermission(Long orgId, Long permissionId);

    /**
     * 删除组特权
     *
     * @param permissionId 权限id
     * @param orgId org id
     */
    void deleteOrgPermission(Long orgId, Long permissionId);

    /**
     * 获取用户菜单
     *
     * @return {@code List<Node<Long>>}
     */
    List<Node<Long>> getUserMenus();

}
