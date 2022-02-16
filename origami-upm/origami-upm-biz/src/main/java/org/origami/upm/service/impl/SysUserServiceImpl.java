package org.origami.upm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.origami.upm.api.dto.SysUserDTO;
import org.origami.upm.api.entity.SysUser;
import org.origami.upm.mapper.SysUserMapper;
import org.origami.upm.service.SysUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author origami
 * @date 2022/1/11 23:09
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements
        SysUserService {


    @Override
    public boolean save(SysUser entity) {
        entity.setPassword(passwordEncoder().encode(entity.getPassword()));
        return super.save(entity);
    }

    @Override
    public SysUserDTO getUserWithRolesByUsername(String username) {
        return Optional.ofNullable(username)
                .map(name -> baseMapper.getUserDTOByUsername(name))
                .orElse(null);
    }

    @Override
    public SysUser getByUsername(String username) {
        return Optional.ofNullable(username)
                .map(s -> baseMapper.selectOne(new QueryWrapper<>(new SysUser().setUsername(
                        s))))
                .orElse(null);
    }

    @Bean
    private PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
