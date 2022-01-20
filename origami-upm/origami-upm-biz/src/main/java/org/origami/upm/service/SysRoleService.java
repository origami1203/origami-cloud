package org.origami.upm.service;

import org.origami.common.mybatis.service.BaseService;
import org.origami.upm.api.entity.SysRole;

import java.util.List;

/**
 * 角色service
 *
 * @author origami
 * @date 2022/1/13 22:04
 */
public interface SysRoleService extends BaseService<SysRole> {
    
    List<SysRole> getListByUserId(Long userId);
    
}
