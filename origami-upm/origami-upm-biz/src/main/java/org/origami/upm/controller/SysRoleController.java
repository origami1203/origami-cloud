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
import org.origami.upm.api.entity.SysRole;
import org.origami.upm.service.SysRoleService;
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
 * 角色管理
 *
 * @author origami
 * @date 2022/1/19 20:12
 */
@Api(tags = "角色接口")
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class SysRoleController {
    
    private final SysRoleService sysRoleService;
    
    @ApiOperation("添加角色")
    @PostMapping("")
    public Result<SysRole> add(@RequestBody SysRole role) {
        return Optional.ofNullable(role)
                       .map(s -> {
                           sysRoleService.save(s);
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
                       .map(sysRoleService::getById)
                       .map(s -> Result.ok())
                       .orElseThrow(BaseException::new);
    }
    
    @ApiOperation("删除指定id用户")
    @DeleteMapping("/{id}")
    @ApiParam(value = "用户id", required = true)
    public Result<Void> deleteById(@PathVariable("id") Long id) {
        return Optional.ofNullable(id)
                       .map(sysRoleService::removeById)
                       .map(s -> Result.ok())
                       .orElseThrow(BaseException::new);
    }
    
    @ApiOperation("更新用户")
    @PutMapping("")
    public Result<SysRole> update(@RequestBody SysRole role) {
        return Optional.ofNullable(role)
                       .map(s -> {
                           sysRoleService.updateById(s);
                           return s;
                       })
                       .map(Result::ok)
                       .orElseThrow(BaseException::new);
    }
    
    @SysLog
    @ApiOperation("分页")
    @GetMapping("")
    public Result<Page<SysRole>> page(PageQueryCondition<SysRole> page) {
        return Optional.ofNullable(page)
                       .map(s -> sysRoleService.page(page))
                       .map(Result::ok)
                       .orElseThrow(BaseException::new);
    }
}
