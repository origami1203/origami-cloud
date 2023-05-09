package org.origami.upm.manager;

import java.util.Map;
import java.util.Set;

/**
 * 用户角色权限manager
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-03-28 13:16
 */
public interface SysRoleManager {
    
    /**
     * 获取所有权限及拥有该权限的所有角色
     *
     * @return {@link Map}<{@link String}, {@link Set}<{@link String}>>
     */
    Map<String, Set<String>> getPermissionRoleMap();
    
    /**
     * 获取所有角色及该角色对应权限
     *
     * @return {@code Map<String, Set<String>>}
     */
    Map<String, Set<String>> getRolePermissionMap();
}
