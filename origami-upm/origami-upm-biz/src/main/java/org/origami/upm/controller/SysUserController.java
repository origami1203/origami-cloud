package org.origami.upm.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.origami.common.core.base.R;
import org.origami.common.core.data.page.IPage;
import org.origami.common.core.data.query.impl.PageDTO;
import org.origami.common.core.exception.NotFoundException;
import org.origami.common.core.exception.base.BaseException;
import org.origami.common.core.utils.Assert;
import org.origami.common.core.utils.BeanUtils;
import org.origami.common.log.annotation.SysLog;
import org.origami.upm.api.dto.SysAuthUserDTO;
import org.origami.upm.api.dto.SysRolePermissionDTO;
import org.origami.upm.api.dto.SysUserParam;
import org.origami.upm.api.entity.SysUserDO;
import org.origami.upm.service.SysUserService;
import org.origami.webmvc.dto.CommandBatchDTO;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
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
    public R<SysUserDO> add(@RequestBody SysUserParam user) {
        return Optional.ofNullable(user).map(it -> BeanUtils.copyProperties(it, SysUserDO.class))
                .map(s -> {
                    sysUserService.save(s);
                    return s;
                }).map(R::ok).orElseThrow(BaseException::new);
    }

    @ApiOperation("获取指定id用户")
    @GetMapping("/user/{id}")
    @ApiParam(value = "用户id", required = true)
    public R<SysUserDO> getById(@PathVariable("id") Long id) {
        return Optional.ofNullable(id).map(sysUserService::getById).map(R::ok)
                .orElseThrow(NotFoundException::new);
    }

    @ApiOperation("通过用户名查询用户")
    @GetMapping("/user/name/{username}")
    @ApiParam(value = "用户名", required = true)
    public R<SysUserDO> getByUsername(@PathVariable("username") String username) {
        return Optional.ofNullable(username)
                .map(sysUserService::getByUsername)
                .map(R::ok)
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("通过用户名查询")
    @GetMapping("/auth-user/{username}")
    @ApiParam(value = "用户id", required = true)
    public R<SysAuthUserDTO> getAuthUserByUsername(@PathVariable("username") String username) {
        return Optional.ofNullable(username)
                .map(sysUserService::getUserWithRolesByUsername)
                .map(R::ok)
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("删除指定id用户")
    @DeleteMapping("/user/{id}")
    @ApiParam(value = "用户id", required = true)
    public R<Boolean> deleteById(@PathVariable("id") Long id) {
        return Optional.ofNullable(id).map(sysUserService::removeById).map(R::ok)
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("更新用户")
    @PutMapping("/user")
    public R<SysUserDO> update(@RequestBody SysUserDO sysUser) {
        return Optional.ofNullable(sysUser).map(s -> {
            sysUserService.updateById(s);
            return s;
        }).map(R::ok).orElseThrow(BaseException::new);
    }

    @SysLog
    @ApiOperation("分页")
    @PostMapping("/users")
    public R<IPage<SysUserDO>> page(@RequestBody PageDTO<SysUserDO> page) {
        return Optional.ofNullable(page)
                .map(s -> sysUserService.page(page.toPageQuery()))
                .map(R::ok)
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("给用户分配角色")
    @PostMapping("/assign-role")
    public R<Void> assignRoles(@RequestBody CommandBatchDTO<Long> dto) {
        return Optional.ofNullable(dto)
                .map(sysUserService::assigningRoles)
                .map(it -> R.ok())
                .orElseThrow(BaseException::new);
    }

    @ApiOperation("测试")
    @PostMapping("/test")
    public R<List<SysUserDO>> test(@RequestBody SysRolePermissionDTO dto) {
        Assert.notEmpty(dto.getPermIds(), "permIds不能为空");

        List<SysUserDO> sysUsers = sysUserService.listByIds(dto.getPermIds());

        System.out.println(sysUsers);

        return R.ok(sysUsers);
    }


}
