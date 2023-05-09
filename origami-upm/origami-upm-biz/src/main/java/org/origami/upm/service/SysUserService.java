package org.origami.upm.service;

import org.origami.common.mybatis.service.BaseService;
import org.origami.upm.api.dto.SysAuthUserDTO;
import org.origami.upm.api.entity.SysUserDO;
import org.origami.webmvc.dto.CommandBatchDTO;

/**
 * @author origami
 * @date 2022/1/11 23:07
 */
public interface SysUserService extends BaseService<SysUserDO> {

    SysUserDO getByUsername(String username);

    SysAuthUserDTO getUserWithRolesByUsername(String username);

    Boolean assigningRoles(CommandBatchDTO<Long> dto);
}
