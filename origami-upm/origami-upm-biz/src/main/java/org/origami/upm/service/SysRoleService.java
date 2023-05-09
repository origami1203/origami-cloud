package org.origami.upm.service;

import org.origami.common.mybatis.service.BaseService;
import org.origami.upm.api.dto.SysRolePermissionDTO;
import org.origami.upm.api.entity.SysRoleDO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色service
 *
 * @author origami
 * @date 2022/1/13 22:04
 */
public interface SysRoleService extends BaseService<SysRoleDO> {

    /**
     * 获取用户拥有的角色
     *
     * @param userId
     * @return
     */
    List<SysRoleDO> getListByUserId(Long userId);


    /**
     * 获取权限和权限对应的角色
     *
     * @return {@link Map}<{@link String}, {@link Set}<{@link String}>>
     */
    Map<String, Set<String>> getPermissionRoleMap();


    /**
     * 给角色分配权限
     *
     * @param dto dto
     */
    boolean assignRolePermission(SysRolePermissionDTO dto);
}
