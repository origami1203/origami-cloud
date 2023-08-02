package org.origami.system.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.origami.common.core.base.BatchOperationRequest;
import org.origami.common.core.base.R;
import org.origami.common.core.data.page.IPage;
import org.origami.common.core.data.query.PageModel;
import org.origami.common.core.exception.NotFoundException;
import org.origami.common.core.exception.base.BaseException;
import org.origami.common.core.utils.TreeUtil.Node;
import org.origami.common.log.annotation.Log;
import org.origami.system.dto.CreatePermissionRequest;
import org.origami.system.dto.UpdatePermissionRequest;
import org.origami.system.entity.SysPermission;
import org.origami.system.service.SysPermissionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

/**
 * 权限接口
 *
 * @author origami
 * @date 2022/1/19 20:17
 */
@Api(tags = "权限接口")
@RestController
@RequestMapping("/sys/perms")
@RequiredArgsConstructor
public class SysPermissionController {

    private final SysPermissionService sysPermissionService;

    @ApiOperation("添加权限")
    @PostMapping("")
    public R<SysPermission> add(@Valid @RequestBody CreatePermissionRequest perm) {
        return Optional.ofNullable(perm).map(sysPermissionService::save).map(R::ok).orElseThrow(BaseException::new);
    }

    @ApiOperation("获取指定id权限")
    @GetMapping("/{id}")
    @ApiParam(value = "权限id", required = true)
    public R<Void> getById(@PathVariable("id") Long id) {
        return Optional.ofNullable(id).map(sysPermissionService::getById).map(s -> R.ok())
            .orElseThrow(BaseException::new);
    }

    @ApiOperation("删除指定id权限")
    @DeleteMapping("/{id}")
    @ApiParam(value = "权限id", required = true)
    public R<Boolean> deleteById(@PathVariable("id") Long id) {
        return Optional.ofNullable(id).map(sysPermissionService::removeById).map(R::ok).orElseThrow(BaseException::new);
    }

    @ApiOperation("更新权限内容")
    @PutMapping("")
    public R<SysPermission> update(@Valid @RequestBody UpdatePermissionRequest request) {
        return Optional.ofNullable(request).map(sysPermissionService::update).map(R::ok)
            .orElseThrow(BaseException::new);
    }

    @Log
    @ApiOperation("权限分页")
    @PostMapping("/page")
    public R<IPage<SysPermission>> page(@RequestBody PageModel<SysPermission> page) {
        return Optional.ofNullable(page).map(sysPermissionService::page).map(R::ok).orElseThrow(BaseException::new);
    }

    @ApiOperation("查询指定角色拥有的权限")
    @GetMapping("/{roleId}/permissions")
    public R<List<SysPermission>> getRolePerms(@PathVariable("roleId") Long roleId) {
        return Optional.ofNullable(roleId).map(sysPermissionService::getRolePerms).map(R::ok)
            .orElseThrow(NotFoundException::new);
    }

    @ApiOperation("给角色添加权限")
    @PostMapping("/role/permissions")
    public R<Boolean> assigningPermsToRole(@RequestBody BatchOperationRequest<Long> request) {
        return Optional.ofNullable(request)
            .map(it -> sysPermissionService.assigningRolePermissions(it.getId(), it.getList())).map(R::ok)
            .orElseThrow(NotFoundException::new);
    }

    @ApiOperation("用户菜单栏")
    @GetMapping("/menus")
    public R<List<Node<Long>>> getUserMenus() {

    }
}
