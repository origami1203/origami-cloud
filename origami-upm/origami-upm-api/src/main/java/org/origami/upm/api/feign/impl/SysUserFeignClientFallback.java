package org.origami.upm.api.feign.impl;

import org.origami.common.core.base.Code;
import org.origami.common.core.base.Result;
import org.origami.common.core.data.page.Page;
import org.origami.common.mybatis.condition.impl.PageQueryCondition;
import org.origami.upm.api.dto.SysUserDTO;
import org.origami.upm.api.entity.SysUser;
import org.origami.upm.api.feign.SysUserFeignClient;

/**
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-21 15:14
 */
public class SysUserFeignClientFallback implements SysUserFeignClient {
    @Override
    public Result<SysUser> add(SysUser user) {
        return Result.failed(Code.SYSTEM_FALLBACK);
    }

    @Override
    public Result<SysUser> getById(Long id) {
        return Result.failed(Code.SYSTEM_FALLBACK);
    }

    @Override
    public Result<Void> deleteById(Long id) {
        return Result.failed(Code.SYSTEM_FALLBACK);
    }

    @Override
    public Result<SysUser> update(SysUser sysUser) {
        return Result.failed(Code.SYSTEM_FALLBACK);
    }

    @Override
    public Result<Page<SysUser>> page(PageQueryCondition<SysUser> page) {
        return Result.failed(Code.SYSTEM_FALLBACK);
    }

    @Override
    public Result<SysUser> getByUsername(String username) {
        return Result.failed(Code.SYSTEM_FALLBACK);
    }

    @Override
    public Result<SysUserDTO> getAuthUserByUsername(String username) {
        return Result.failed(Code.SYSTEM_FALLBACK);
    }
}
