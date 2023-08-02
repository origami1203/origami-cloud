package org.origami.system.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.origami.boot.mybatisplus.service.impl.ServiceImpl;
import org.origami.common.core.base.BatchOperationRequest;
import org.origami.common.core.data.page.IPage;
import org.origami.common.core.data.query.PageModel;
import org.origami.common.core.utils.Assert;
import org.origami.common.core.utils.BeanUtils;
import org.origami.system.dto.SysRoleDTO;
import org.origami.system.dto.SysRoleFormRequest;
import org.origami.system.entity.SysRoleDO;
import org.origami.system.manager.SysRoleManager;
import org.origami.system.mapper.SysRoleMapper;
import org.origami.system.mapper.SysUserRoleMapper;
import org.origami.system.service.SysRoleService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import lombok.RequiredArgsConstructor;

/**
 * @author origami
 * @date 2022/1/13 22:05
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleDO> implements SysRoleService {

    private final SysRoleManager sysRoleManager;
    private final SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysRoleDTO> getUserRoles(Long userId) {
        return baseMapper.selectListByUserId(userId).stream()
            .map(entity -> BeanUtils.copyProperties(entity, SysRoleDTO.class)).collect(Collectors.toList());
    }

    @Override
    public boolean save(SysRoleDO entity) {
        checkDataExists(entity);
        return super.save(entity);
    }

    @Override
    public boolean save(SysRoleFormRequest roleFormRequest) {
        SysRoleDO entity = BeanUtils.copyProperties(roleFormRequest, SysRoleDO.class);
        return save(entity);
    }

    @Override
    public boolean updateById(SysRoleDO entity) {
        checkDataExists(entity);
        return super.updateById(entity);
    }

    @Override
    public boolean update(SysRoleDTO dto) {
        SysRoleDO sysRoleDO = BeanUtils.copyProperties(dto, SysRoleDO.class);
        return updateById(sysRoleDO);
    }

    @Override
    public boolean removeById(Long id) {
        Set<Long> userIds = sysUserRoleMapper.selectCountByRoleId(id);
        Assert.isEmpty(userIds, "存在用户绑定此角色,无法删除");
        return super.removeById(id);
    }

    @Override
    public IPage<SysRoleDTO> pageByCondition(PageModel<SysRoleDTO> dto) {
        PageModel<SysRoleDO> pageModel = dto.converter(SysRoleDO.class);
        return page(pageModel).map(it -> BeanUtils.copyProperties(it, SysRoleDTO.class));
    }

    @Override
    public boolean assigningRolesToUser(BatchOperationRequest<Long> dto) {
        return sysRoleManager.assignRolesToUser(dto.getId(), dto.getList());
    }

    @Override
    public boolean assigningRolesToOrganization(BatchOperationRequest<Long> dto) {
        return sysRoleManager.assignRolesToOrganization(dto.getId(), dto.getList());
    }

    private void checkDataExists(SysRoleDO sysRoleDO) {
        if (sysRoleDO.getRoleCode() != null) {
            Wrapper<SysRoleDO> wrapper =
                new LambdaQueryWrapper<SysRoleDO>().eq(SysRoleDO::getRoleCode, sysRoleDO.getRoleCode());

            Long count = baseMapper.selectCount(wrapper);
            Assert.isTrue(count == 0, "角色编码已存在");
        }

        if (sysRoleDO.getRoleName() != null) {
            Wrapper<SysRoleDO> wrapper =
                new LambdaQueryWrapper<SysRoleDO>().eq(SysRoleDO::getRoleName, sysRoleDO.getRoleName());

            Long count = baseMapper.selectCount(wrapper);
            Assert.isTrue(count == 0, "角色名称已存在");
        }
    }
}
