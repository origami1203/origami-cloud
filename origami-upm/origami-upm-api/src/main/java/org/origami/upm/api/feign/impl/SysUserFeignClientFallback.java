package org.origami.upm.api.feign.impl;

import org.origami.common.core.base.CodeEnum;
import org.origami.common.core.base.R;
import org.origami.common.core.data.page.IPage;
import org.origami.common.core.data.query.PageQuery;
import org.origami.upm.api.dto.SysAuthUserDTO;
import org.origami.upm.api.entity.SysUserDO;
import org.origami.upm.api.feign.RemoteSysUserService;

/**
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-21 15:14
 */
public class SysUserFeignClientFallback implements RemoteSysUserService {
    @Override
    public R<SysUserDO> add(SysUserDO user) {
        return R.failed(CodeEnum.SYSTEM_FALLBACK);
    }

    @Override
    public R<SysUserDO> getById(Long id) {
        return R.failed(CodeEnum.SYSTEM_FALLBACK);
    }

    @Override
    public R<Void> deleteById(Long id) {
        return R.failed(CodeEnum.SYSTEM_FALLBACK);
    }

    @Override
    public R<SysUserDO> update(SysUserDO sysUser) {
        return R.failed(CodeEnum.SYSTEM_FALLBACK);
    }

    @Override
    public R<IPage<SysUserDO>> page(PageQuery<SysUserDO> page) {
        return R.failed(CodeEnum.SYSTEM_FALLBACK);
    }

    @Override
    public R<SysUserDO> getByUsername(String username) {
        return R.failed(CodeEnum.SYSTEM_FALLBACK);
    }

    @Override
    public R<SysAuthUserDTO> getAuthUserByUsername(String username) {
        return R.failed(CodeEnum.SYSTEM_FALLBACK);
    }
}
