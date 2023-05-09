package org.origami.upm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.origami.common.core.base.R;
import org.origami.common.core.data.page.IPage;
import org.origami.common.core.data.query.PageQuery;
import org.origami.common.core.exception.base.BaseException;
import org.origami.common.log.annotation.SysLog;
import org.origami.upm.api.entity.SysPermission;
import org.origami.upm.service.SysPermissionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * 权限接口
 *
 * @author origami
 * @date 2022/1/19 20:17
 */
@Api(tags = "权限接口")
@RestController
@RequestMapping("/perms")
@RequiredArgsConstructor
public class SysPermissionController {

    private final SysPermissionService sysPermissionService;

    @ApiOperation("添加权限")
    @PostMapping("")
    public R<SysPermission> add(@Validated @RequestBody SysPermission perm) {
        return Optional.ofNullable(perm)
                .map(s -> {
                    sysPermissionService.save(s);
                    return s;
                })
                .map(R::ok)
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("获取指定id权限")
    @GetMapping("/{id}")
    @ApiParam(value = "权限id", required = true)
    public R<Void> getById(@PathVariable("id") Long id) {
        return Optional.ofNullable(id)
                .map(sysPermissionService::getById)
                .map(s -> R.ok())
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("删除指定id权限")
    @DeleteMapping("/{id}")
    @ApiParam(value = "权限id", required = true)
    public R<Boolean> deleteById(@PathVariable("id") Long id) {
        return Optional.ofNullable(id)
                .map(sysPermissionService::removeById)
                .map(R::ok)
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("更新权限内容")
    @PutMapping("")
    public R<SysPermission> update(@RequestBody SysPermission perm) {
        return Optional.ofNullable(perm)
                .map(s -> {
                    sysPermissionService.updateById(s);
                    return s;
                })
                .map(R::ok)
                .orElseThrow(BaseException::new);
    }

    @SysLog
    @ApiOperation("分页")
    @GetMapping("")
    public R<IPage<SysPermission>> page(PageQuery<SysPermission> page) {
        return Optional.ofNullable(page)
                .map(sysPermissionService::page)
                .map(R::ok)
                .orElseThrow(BaseException::new);
    }
}
