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
import org.origami.upm.api.dto.SysUserDTO;
import org.origami.upm.api.entity.SysUser;
import org.origami.upm.service.SysUserService;
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
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-14 11:00
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/sys")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    @ApiOperation("添加用户")
    @PostMapping("/user")
    public Result<SysUser> add(@RequestBody SysUser user) {
        return Optional.ofNullable(user)
                .map(s -> {
                    sysUserService.save(s);
                    return s;
                })
                .map(Result::ok)
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("获取指定id用户")
    @GetMapping("/user/{id}")
    @ApiParam(value = "用户id", required = true)
    public Result<SysUser> getById(@PathVariable("id") Long id) {
        return Optional.ofNullable(id)
                .map(sysUserService::getById)
                .map(Result::ok)
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("获取用户名查询用户")
    @GetMapping("/user/name/{username}")
    @ApiParam(value = "用户名", required = true)
    public Result<SysUser> getByUsername(@PathVariable("username") String username) {
        return Optional.ofNullable(username)
                .map(sysUserService::getByUsername)
                .map(Result::ok)
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("通过用户名查询")
    @GetMapping("/authuser/{username}")
    @ApiParam(value = "用户id", required = true)
    public Result<SysUserDTO> getAuthUserByUsername(@PathVariable("username") String username) {
        return Optional.ofNullable(username)
                .map(sysUserService::getUserWithRolesByUsername)
                .map(Result::ok)
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("删除指定id用户")
    @DeleteMapping("/user/{id}")
    @ApiParam(value = "用户id", required = true)
    public Result<Void> deleteById(@PathVariable("id") Long id) {
        return Optional.ofNullable(id)
                .map(sysUserService::removeById)
                .map(s -> Result.ok())
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("更新用户")
    @PutMapping("/user")
    public Result<SysUser> update(@RequestBody SysUser sysUser) {
        return Optional.ofNullable(sysUser)
                .map(s -> {
                    sysUserService.updateById(s);
                    return s;
                })
                .map(Result::ok)
                .orElseThrow(BaseException::new);
    }

    @SysLog
    @ApiOperation("分页")
    @GetMapping("/users")
    public Result<Page<SysUser>> page(PageQueryCondition<SysUser> page) {
        return Optional.ofNullable(page)
                .map(s -> sysUserService.page(page))
                .map(Result::ok)
                .orElseThrow(BaseException::new);
    }

    @SysLog
    @ApiOperation("测试")
    @GetMapping("/test")
    public Result<Void> test() {
        return Result.ok();
    }
}
