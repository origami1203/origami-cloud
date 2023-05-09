package org.origami.upm.api.feign;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.origami.common.core.base.R;
import org.origami.common.core.data.page.IPage;
import org.origami.common.core.data.query.PageQuery;
import org.origami.upm.api.dto.SysAuthUserDTO;
import org.origami.upm.api.entity.SysUserDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 系统用户远程调用
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-21 14:54
 */
@FeignClient(value = "origami-upm",path = "/sys")
public interface RemoteSysUserService {

    @ApiOperation("添加用户")
    @PostMapping("/user")
    R<SysUserDO> add(@RequestBody SysUserDO user);

    @ApiOperation("获取指定id用户")
    @GetMapping("/user/{id}")
    @ApiParam(value = "用户id", required = true)
    R<SysUserDO> getById(@PathVariable("id") Long id);

    @ApiOperation("删除指定id用户")
    @DeleteMapping("/user/{id}")
    @ApiParam(value = "用户id", required = true)
    R<Void> deleteById(@PathVariable("id") Long id);

    @ApiOperation("更新用户")
    @PutMapping("/user")
    R<SysUserDO> update(@RequestBody SysUserDO sysUser);

    @ApiOperation("分页")
    @GetMapping("/users")
    R<IPage<SysUserDO>> page(PageQuery<SysUserDO> page);

    @ApiOperation("通过用户名查询权限用户")
    @GetMapping("/authuser/{username}")
    @ApiParam(value = "用户名", required = true)
    R<SysAuthUserDTO> getAuthUserByUsername(@PathVariable("username") String username);

    @ApiOperation("获取用户名查询用户")
    @GetMapping("/user/name/{username}")
    @ApiParam(value = "用户名", required = true)
    R<SysUserDO> getByUsername(@PathVariable("username") String username);
}
