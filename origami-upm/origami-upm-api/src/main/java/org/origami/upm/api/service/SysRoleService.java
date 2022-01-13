package org.origami.upm.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.origami.upm.api.entity.SysRole;

import java.util.List;

/**
 * 角色service
 *
 * @author origami
 * @date 2022/1/13 22:04
 */
public interface SysRoleService extends IService<SysRole> {
    
    List<SysRole> getListByUserId(Long userId);
    
}
