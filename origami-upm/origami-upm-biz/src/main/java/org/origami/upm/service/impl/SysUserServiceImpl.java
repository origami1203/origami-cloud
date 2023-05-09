package org.origami.upm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.origami.common.core.exception.IllegalParamException;
import org.origami.common.core.exception.NotFoundException;
import org.origami.upm.api.dto.SysAuthUserDTO;
import org.origami.upm.api.entity.SysRoleDO;
import org.origami.upm.api.entity.SysUserDO;
import org.origami.upm.api.entity.SysUserRoleDO;
import org.origami.upm.mapper.SysUserMapper;
import org.origami.upm.mapper.SysUserRoleMapper;
import org.origami.upm.service.SysDeptService;
import org.origami.upm.service.SysRoleService;
import org.origami.upm.service.SysUserService;
import org.origami.webmvc.dto.CommandBatchDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author origami
 * @date 2022/1/11 23:09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserDO> implements
                                                                            SysUserService {

    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleService sysRoleService;
    private final SysDeptService sysDeptService;

    @Override
    public boolean save(SysUserDO entity) {
        entity.setPassword(passwordEncoder().encode(entity.getPassword()));

        Optional.ofNullable(sysDeptService.getById(entity.getDeptId()))
                .orElseThrow(() -> new NotFoundException("部门不存在"));

        return super.save(entity);
    }

    @Override
    public Boolean assigningRoles(CommandBatchDTO<Long> dto) {
        List<Long> roles = dto.getList();

        SysUserDO sysUser = Optional.ofNullable(baseMapper.selectById(dto.getId()))
                                  .orElseThrow(NotFoundException::new);

        if (roles.isEmpty()) {
            sysUserRoleMapper.deleteByUserId(dto.getId());
            return true;
        }

        List<SysRoleDO> sysRoles = sysRoleService.listByIds(roles);

        if (roles.size() != sysRoles.size()) {
            throw new IllegalParamException("需要分配的权限id中有未找到的权限");
        }

        List<SysUserRoleDO> sysUserRoles = sysRoles.stream().map(it -> {
            SysUserRoleDO sysUserRole = new SysUserRoleDO();
            return sysUserRole.setUserId(dto.getId()).setRoleId(it.getId());
        }).collect(Collectors.toList());

        sysUserRoleMapper.insertBatchSomeColumn(sysUserRoles);

        return true;
    }

    @Override
    public SysAuthUserDTO getUserWithRolesByUsername(String username) {
        return Optional.ofNullable(username)
                       .map(name -> baseMapper.getUserDTOByUsername(name))
                       .orElse(null);
    }

    @Override
    public SysUserDO getByUsername(String username) {
        return Optional.ofNullable(username)
                       .map(s -> baseMapper.selectOne(new QueryWrapper<>(new SysUserDO().setUsername(
                               s))))
                       .orElse(null);
    }

    @Bean
    private PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
