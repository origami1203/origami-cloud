package org.origami.system.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.origami.common.core.base.CodeEnum;
import org.origami.common.core.base.R;
import org.origami.common.core.data.page.IPage;
import org.origami.common.core.data.query.PageModel;
import org.origami.common.core.exception.NotFoundException;
import org.origami.common.core.exception.base.BaseException;
import org.origami.common.core.utils.TreeUtil;
import org.origami.common.core.utils.TreeUtil.Node;
import org.origami.common.log.annotation.Log;
import org.origami.security.util.SecurityUserHelper;
import org.origami.system.dto.LoginUser;
import org.origami.system.dto.SysUserDTO;
import org.origami.system.entity.SysPermission;
import org.origami.system.service.SysUserService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

/**
 * @author origami
 * @version 1.0.0
 * @date 2022-01-14 11:00
 */
@Api(tags = "用户接口")
@RestController
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    // @ApiOperation("添加用户(注册)")
    // @PostMapping("/register")
    // public R<Boolean> add(@Valid @RequestBody CreateUserRequest user) {
    // return Optional.ofNullable(user).map(sysUserService::save).map(R::ok).orElseThrow(BaseException::new);
    // }

    @ApiOperation("获取指定id用户")
    @GetMapping("/sys/user/{id}")
    @ApiParam(value = "用户id", required = true)
    public R<SysUserDTO> getById(@PathVariable("id") Long id) {
        return Optional.ofNullable(id).map(sysUserService::findById).map(R::ok).orElseThrow(NotFoundException::new);
    }

    @ApiOperation("通过用户名查询用户")
    @GetMapping("/sys/user/username/{username}")
    @ApiParam(value = "用户名", required = true)
    public R<SysUserDTO> getByUsername(@PathVariable("username") String username) {
        return Optional.ofNullable(username).map(sysUserService::getUserByUsername).map(R::ok)
            .orElseThrow(NotFoundException::new);
    }

    @ApiOperation("通过用户名查询权限用户")
    @GetMapping("/sys/user/auth-user/{username}")
    @ApiParam(value = "用户id", required = true)
    public R<LoginUser> getAuthUserByUsername(@PathVariable("username") String username) {
        return Optional.ofNullable(username).map(sysUserService::getLoginUserByUsername).map(R::ok)
            .orElseThrow(BaseException::new);
    }

    @ApiOperation("删除指定id用户")
    @DeleteMapping("/sys/user/{id}")
    @ApiParam(value = "用户id", required = true)
    public R<Boolean> deleteById(@PathVariable("id") Long id) {
        return Optional.ofNullable(id).map(sysUserService::removeById).map(R::ok).orElseThrow(BaseException::new);
    }

    @ApiOperation("更新用户")
    @PutMapping("/sys/user")
    public R<Boolean> update(@Valid @RequestBody SysUserDTO sysUser) {
        return Optional.ofNullable(sysUser).map(s -> sysUserService.updateUserDetail(sysUser)).map(R::ok)
            .orElseThrow(BaseException::new);
    }

    @Log
    @ApiOperation("用户分页")
    @PostMapping("/sys/user/page")
    public R<IPage<SysUserDTO>> page(@RequestBody PageModel<SysUserDTO> page) {
        return Optional.ofNullable(page).map(s -> sysUserService.pageByCondition(page)).map(R::ok)
            .orElseThrow(BaseException::new);
    }

    @ApiOperation("用户信息")
    @GetMapping("/sys/user/info")
    public R<SysUserDTO> userInfo() {
        Long userId = Optional.ofNullable(SecurityUserHelper.getCurrentUserId())
            .orElseThrow(() -> new BaseException(CodeEnum.USER_NOT_LOGIN));

        return R.ok(sysUserService.findById(userId));
    }

}
