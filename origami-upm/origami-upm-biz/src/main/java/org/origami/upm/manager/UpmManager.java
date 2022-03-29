package org.origami.upm.manager;

import java.util.Map;
import java.util.Set;

/**
 * 用户角色权限manager
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-03-28 13:16
 */
public interface UpmManager {

    Map<String, Set<String>> getPermissionRoleMap();

}
