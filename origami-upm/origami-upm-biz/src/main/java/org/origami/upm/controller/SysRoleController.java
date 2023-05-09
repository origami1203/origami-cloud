package org.origami.upm.controller;

import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.origami.common.core.base.R;
import org.origami.common.core.data.page.IPage;
import org.origami.common.core.data.query.PageQuery;
import org.origami.common.core.exception.base.BaseException;
import org.origami.upm.api.dto.SysRolePermissionDTO;
import org.origami.upm.api.entity.SysRoleDO;
import org.origami.upm.service.SysRoleService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色管理
 *
 * @author origami
 * @date 2022/1/19 20:12
 */
@Api(tags = "角色接口")
@RestController
@RequestMapping("/sys/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @ApiOperation("添加角色")
    @PostMapping("")
    public R<SysRoleDO> add(@RequestBody SysRoleDO role) {
        return Optional.ofNullable(role)
                .map(s -> {
                    sysRoleService.save(s);
                    return s;
                })
                .map(R::ok)
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("获取指定id角色")
    @GetMapping("/{id}")
    @ApiParam(value = "角色id", required = true)
    public R<SysRoleDO> getById(@PathVariable("id") Long id) {
        return Optional.ofNullable(id)
                .map(sysRoleService::getById)
                .map(R::ok)
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("删除指定id角色")
    @DeleteMapping("/{id}")
    @ApiParam(value = "角色id", required = true)
    public R<Void> deleteById(@PathVariable("id") Long id) {
        return Optional.ofNullable(id)
                .map(sysRoleService::removeById)
                .map(s -> R.ok())
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("更新角色")
    @PutMapping("")
    public R<SysRoleDO> update(@RequestBody SysRoleDO role) {
        return Optional.ofNullable(role)
                .map(s -> {
                    sysRoleService.updateById(s);
                    return s;
                })
                .map(R::ok)
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("分页")
    @GetMapping("")
    public R<IPage<SysRoleDO>> page(PageQuery<SysRoleDO> page) {
        return Optional.ofNullable(page)
                .map(s -> sysRoleService.page(page))
                .map(R::ok)
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("获取所有的权限及该权限对应的角色")
    @GetMapping("/permRoles")
    public R<Map<String, Set<String>>> getPermissionRoleMap() {
        return Optional.ofNullable(sysRoleService.getPermissionRoleMap())
                .map(R::ok)
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("给角色分配权限")
    @PostMapping("/assign-permission")
    public R<Void> assignRolePermission(@RequestBody SysRolePermissionDTO dto) {
        return Optional.ofNullable(dto)
                .map(sysRoleService::assignRolePermission)
                .map(it -> R.ok())
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("获取所有的权限及该权限对应的角色")
    @GetMapping("/test")
    public R<Set<ConfigAttribute>> getPermissionRoleMap(HttpServletRequest httpRequest) {
        Set<ConfigAttribute> needRoles = new HashSet<>();
        String uri = httpRequest.getRequestURI();

        AntPathMatcher antPathMatcher = new AntPathMatcher();

        Map<String, Set<String>> permRoleMap = sysRoleService.getPermissionRoleMap();

        permRoleMap.entrySet().stream()
                .filter(s -> !"/**".equals(s.getKey()))
                .forEach(s -> {
                    if (antPathMatcher.match(s.getKey(), uri)) {
                        needRoles.addAll(s.getValue()
                                .stream()
                                .map(SecurityConfig::new)
                                .collect(
                                        Collectors.toSet()));
                    }
                });

        // 如果url没有配置，默认该url需要拥有user权限
        if (CollUtil.isEmpty(needRoles)) {
            needRoles.add(new SecurityConfig("ROLE_USER"));
        }

        return R.ok(needRoles);
    }


}
