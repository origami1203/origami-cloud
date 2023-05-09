package org.origami.upm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.origami.upm.api.entity.SysRoleDO;

import java.util.List;

/**
 * @author origami
 * @date 2022/1/11 23:01
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleDO> {

    /**
     * 指定用户的角色
     */
    List<SysRoleDO> selectListByUserId(Long userId);

    /**
     * 根据权限获取角色
     * @param permission
     * @return
     */
    List<SysRoleDO> selectRolesByPermission(String permission);
}
