package org.origami.upm.api.feign;

import io.swagger.annotations.ApiOperation;
import org.origami.common.core.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.Set;

/**
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-03-28 11:01
 */
@FeignClient(value = "origami-upm", path = "/sys/role")
public interface RemoteSysRoleService {

    @ApiOperation("获取所有的权限及该权限对应的角色")
    @GetMapping("/permRoles")
    R<Map<String, Set<String>>> getPermissionRoleMap();
}
