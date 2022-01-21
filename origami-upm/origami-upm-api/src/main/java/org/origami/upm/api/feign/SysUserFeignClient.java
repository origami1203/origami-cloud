package org.origami.upm.api.feign;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.origami.common.core.base.Result;
import org.origami.common.core.data.page.Page;
import org.origami.common.mybatis.condition.impl.PageQueryCondition;
import org.origami.upm.api.dto.SysUserDTO;
import org.origami.upm.api.entity.SysUser;
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
public interface SysUserFeignClient {

    @ApiOperation("添加用户")
    @PostMapping("/user")
    Result<SysUser> add(@RequestBody SysUser user);

    @ApiOperation("获取指定id用户")
    @GetMapping("/user/{id}")
    @ApiParam(value = "用户id", required = true)
    Result<SysUser> getById(@PathVariable("id") Long id);

    @ApiOperation("删除指定id用户")
    @DeleteMapping("/user/{id}")
    @ApiParam(value = "用户id", required = true)
    Result<Void> deleteById(@PathVariable("id") Long id);

    @ApiOperation("更新用户")
    @PutMapping("/user")
    Result<SysUser> update(@RequestBody SysUser sysUser);

    @ApiOperation("分页")
    @GetMapping("/users")
    Result<Page<SysUser>> page(PageQueryCondition<SysUser> page);

    @ApiOperation("通过用户名查询")
    @GetMapping("/user/{username}")
    @ApiParam(value = "用户id", required = true)
    Result<SysUserDTO> getAuthUserByUsername(@PathVariable("username") String username);
}
