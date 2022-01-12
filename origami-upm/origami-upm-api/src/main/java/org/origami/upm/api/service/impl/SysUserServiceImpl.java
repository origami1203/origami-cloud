package org.origami.upm.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.origami.upm.api.dto.SysUserDTO;
import org.origami.upm.api.entity.SysUser;
import org.origami.upm.api.mapper.SysUserMapper;
import org.origami.upm.api.service.SysUserService;
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
}
