package org.origami.system.controller;

import io.swagger.annotations.ApiImplicitParam;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.origami.common.core.base.R;
import org.origami.common.core.data.page.IPage;
import org.origami.common.core.data.query.PageModel;
import org.origami.common.core.exception.NotFoundException;
import org.origami.common.core.exception.base.BaseException;
import org.origami.system.dto.SysRoleDTO;
import org.origami.system.dto.SysRoleFormRequest;
import org.origami.system.entity.SysRoleDO;
import org.origami.system.service.SysRoleService;
import org.origami.common.core.base.BatchOperationRequest;
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
    public R<Boolean> add(@Valid @RequestBody SysRoleFormRequest role) {
        return Optional.ofNullable(role).map(sysRoleService::save).map(R::ok).orElseThrow(BaseException::new);
    }

    @ApiOperation("获取指定id角色")
    @GetMapping("/{id}")
    @ApiParam(value = "角色id", required = true)
    public R<SysRoleDO> getById(@PathVariable("id") Long id) {
        return Optional.ofNullable(id).map(sysRoleService::getById).map(R::ok).orElseThrow(NotFoundException::new);
    }

    @ApiOperation("删除指定id角色")
    @DeleteMapping("/{id}")
    @ApiParam(value = "角色id", required = true)
    public R<Void> deleteById(@PathVariable("id") Long id) {
        return Optional.ofNullable(id).map(sysRoleService::removeById).map(s -> R.ok()).orElseThrow(BaseException::new);
    }

    @ApiOperation("更新角色")
    @PutMapping("")
    public R<Boolean> update(@Valid @RequestBody SysRoleDTO role) {
        return Optional.ofNullable(role).map(sysRoleService::update).map(R::ok).orElseThrow(BaseException::new);
    }

    @ApiOperation("角色分页")
    @PostMapping("/page")
    public R<IPage<SysRoleDTO>> page(@RequestBody PageModel<SysRoleDTO> page) {
        return Optional.ofNullable(page).map(s -> sysRoleService.pageByCondition(page)).map(R::ok)
            .orElseThrow(BaseException::new);
    }

    @ApiOperation("给用户分配角色")
    @PostMapping("/assign-role/user")
    public R<Void> assignRolesToUser(@Valid @RequestBody BatchOperationRequest<Long> dto) {
        return Optional.ofNullable(dto).map(sysRoleService::assigningRolesToUser).map(it -> R.ok())
            .orElseThrow(BaseException::new);
    }

    @ApiOperation("获取用户角色")
    @GetMapping("/user/{id}")
    public R<List<SysRoleDTO>> getUserRoles(@ApiParam(required = true, value = "用户id") @PathVariable("id") Long id) {
        return Optional.of(id).map(sysRoleService::getUserRoles).map(R::ok).orElseThrow(BaseException::new);
    }

}
