package org.origami.upm.service;

import org.origami.common.mybatis.service.BaseService;
import org.origami.upm.api.entity.SysRole;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色service
 *
 * @author origami
 * @date 2022/1/13 22:04
 */
public interface SysRoleService extends BaseService<SysRole> {

    /**
     * 获取用户拥有的角色
     *
     * @param userId
     * @return
     */
    List<SysRole> getListByUserId(Long userId);


    Map<String, Set<String>> getPermissionRoleMap();
}
