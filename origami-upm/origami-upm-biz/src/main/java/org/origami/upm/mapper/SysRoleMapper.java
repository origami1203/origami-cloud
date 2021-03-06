package org.origami.upm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.origami.upm.api.entity.SysRole;

import java.util.List;

/**
 * @author origami
 * @date 2022/1/11 23:01
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    
    /**
     * 指定用户的角色
     */
    List<SysRole> selectListByUserId(Long userId);

    /**
     * 根据权限获取角色
     * @param permission
     * @return
     */
    List<SysRole> selectRolesByPermission(String permission);
}
