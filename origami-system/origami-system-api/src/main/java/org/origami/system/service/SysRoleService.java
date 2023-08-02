package org.origami.system.service;

import java.util.List;

import org.origami.boot.mybatisplus.service.BaseService;
import org.origami.common.core.base.BatchOperationRequest;
import org.origami.common.core.data.page.IPage;
import org.origami.common.core.data.query.PageModel;
import org.origami.system.dto.SysRoleDTO;
import org.origami.system.dto.SysRoleFormRequest;
import org.origami.system.entity.SysRoleDO;
import org.springframework.transaction.annotation.Transactional;

/**
 * 角色service
 *
 * @author origami
 * @date 2022/1/13 22:04
 */
public interface SysRoleService extends BaseService<SysRoleDO> {

    /**
     * 获取用户拥有的所有角色
     *
     * @param userId 用户id
     * @return {@code List<SysRoleDO>}
     */
    List<SysRoleDTO> getUserRoles(Long userId);

    /**
     * 保存
     *
     * @param roleFormRequest form请求
     * @return boolean
     */
    boolean save(SysRoleFormRequest roleFormRequest);

    /**
     * 更新
     *
     * @param dto dto
     * @return boolean
     */
    boolean update(SysRoleDTO dto);

    /**
     * 条件分页查询
     *
     * @param dto dto
     * @return {@code IPage<SysRoleDTO>}
     */
    IPage<SysRoleDTO> pageByCondition(PageModel<SysRoleDTO> dto);

    /**
     * 给指定用户分配角色
     *
     * @param dto dto
     * @return {@code Boolean}
     */
    @Transactional(rollbackFor = Exception.class)
    boolean assigningRolesToUser(BatchOperationRequest<Long> dto);

    /**
     * 给组织架构分配角色
     *
     * @param dto dto
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    boolean assigningRolesToOrganization(BatchOperationRequest<Long> dto);
}
