package org.origami.upm.service;

import org.origami.common.mybatis.service.BaseService;
import org.origami.upm.api.dto.SysUserDTO;
import org.origami.upm.api.entity.SysUser;

/**
 * @author origami
 * @date 2022/1/11 23:07
 */
public interface SysUserService extends BaseService<SysUser> {
    
    SysUser getByUsername(String username);
    
    SysUserDTO getUserWithRolesByUsername(String username);
}
