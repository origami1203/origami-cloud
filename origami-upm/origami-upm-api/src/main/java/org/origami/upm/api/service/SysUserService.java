package org.origami.upm.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.origami.upm.api.dto.SysUserDTO;
import org.origami.upm.api.entity.SysUser;

/**
 * @author origami
 * @date 2022/1/11 23:07
 */
public interface SysUserService extends IService<SysUser> {
    SysUser getByUsername(String username);
    
    SysUserDTO getUserWithRolesByUsername(String username);
}
