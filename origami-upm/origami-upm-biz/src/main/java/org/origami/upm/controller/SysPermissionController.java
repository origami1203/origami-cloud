package org.origami.upm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.origami.common.core.base.Result;
import org.origami.common.core.data.page.Page;
import org.origami.common.core.exception.base.BaseException;
import org.origami.common.log.annotation.SysLog;
import org.origami.common.mybatis.condition.impl.PageQueryCondition;
import org.origami.upm.api.entity.SysPermission;
import org.origami.upm.service.SysPermissionService;
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
    
    @ApiOperation("添加角色")
    @PostMapping("")
    public Result<SysPermission> add(@RequestBody SysPermission perm) {
        return Optional.ofNullable(perm)
                       .map(s -> {
                           sysPermissionService.save(s);
                           return s;
                       })
                       .map(Result::ok)
                       .orElseThrow(BaseException::new);
    }
    
    @ApiOperation("获取指定id用户")
    @GetMapping("/{id}")
    @ApiParam(value = "用户id", required = true)
    public Result<Void> getById(@PathVariable("id") Long id) {
        return Optional.ofNullable(id)
                       .map(sysPermissionService::getById)
                       .map(s -> Result.ok())
                       .orElseThrow(BaseException::new);
    }
    
    @ApiOperation("删除指定id用户")
    @DeleteMapping("/{id}")
    @ApiParam(value = "用户id", required = true)
    public Result<Void> deleteById(@PathVariable("id") Long id) {
        return Optional.ofNullable(id)
                       .map(sysPermissionService::removeById)
                       .map(s -> Result.ok())
                       .orElseThrow(BaseException::new);
    }
    
    @ApiOperation("更新用户")
    @PutMapping("")
    public Result<SysPermission> update(@RequestBody SysPermission perm) {
        return Optional.ofNullable(perm)
                       .map(s -> {
                           sysPermissionService.updateById(s);
                           return s;
                       })
                       .map(Result::ok)
                       .orElseThrow(BaseException::new);
    }
    
    @SysLog
    @ApiOperation("分页")
    @GetMapping("")
    public Result<Page<SysPermission>> page(PageQueryCondition<SysPermission> page) {
        return Optional.ofNullable(page)
                       .map(s -> sysPermissionService.page(page))
                       .map(Result::ok)
                       .orElseThrow(BaseException::new);
    }
}
