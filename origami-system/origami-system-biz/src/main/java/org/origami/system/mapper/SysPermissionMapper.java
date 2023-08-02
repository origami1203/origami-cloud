package org.origami.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.origami.boot.mybatisplus.mapper.BaseMapper;
import org.origami.system.dto.SysMenuDTO;
import org.origami.system.entity.SysPermission;

/**
 * @author origami
 * @date 2022/1/11 23:02
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 指定角色的权限
     *
     * @param roleId 角色id
     * @return {@code List<SysPermission>}
     */
    List<SysPermission> selectPermissionsByRoleId(Long roleId);

    /**
     * 获取指定用户的所有权限列表
     *
     * @param username 用户名
     * @return {@code List<SysPermission>}
     */
    List<SysPermission> selectPermissionListByUsername(@Param("username") String username);

    /**
     * 删除角色权限
     *
     * @param roleId 角色id
     * @param permIds 权限id
     */
    void deleteRolePermissions(@Param("roleId") Long roleId, @Param("permIds") List<Long> permIds);

    /**
     * 删除角色所有权限
     *
     * @param roleId 角色id
     */
    void deleteRoleAllPermissions(@Param("roleId") Long roleId);


    List<SysMenuDTO>
}
