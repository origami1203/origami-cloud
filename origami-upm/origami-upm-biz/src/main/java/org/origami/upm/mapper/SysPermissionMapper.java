package org.origami.upm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.origami.upm.api.entity.SysPermission;

import java.util.List;

/**
 * @author origami
 * @date 2022/1/11 23:02
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 指定角色的权限
     */
    List<SysPermission> selectListByRoleId(Long roleId);
}
