package org.origami.system.mapper;

import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.origami.boot.mybatisplus.mapper.BaseMapper;
import org.origami.system.entity.SysRoleDO;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author origami
 * @date 2022/1/11 23:01
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleDO> {

    /**
     * 查看指定用户的角色
     *
     * @param userId 用户id
     * @return {@code List<SysRoleDO>}
     */
    List<SysRoleDO> selectListByUserId(@Param("userId") Long userId);

    /**
     * 查看指定组织的角色
     *
     * @param organizationId 组织id
     * @return {@code List<SysRoleDO>}
     */
    List<SysRoleDO> selectListByOrganizationId(@Param("organizationId") Long organizationId);

    /**
     * 根据权限获取角色
     *
     * @param permissionId 许可
     * @return {@code List<SysRoleDO>}
     */
    List<SysRoleDO> selectRolesByPermissionId(@Param("permissionId") Long permissionId);


    /**
     * 删除organization所有角色
     *
     * @param organizationId 组织id
     * @return int
     */
    @Transactional(rollbackFor = Exception.class)
    int deleteOrganizationAllRoles(@Param("organizationId") Long organizationId);


    /**
     * 查询哪些用户拥有指定角色
     *
     * @param roleId 角色id
     * @return int
     */
    Set<Long> selectCountByRoleId(@Param("roleId") Long roleId);
}
