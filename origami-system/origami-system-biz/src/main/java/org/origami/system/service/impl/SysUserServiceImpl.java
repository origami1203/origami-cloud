package org.origami.system.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.origami.boot.mybatisplus.service.impl.ServiceImpl;
import org.origami.common.core.data.page.IPage;
import org.origami.common.core.data.query.PageModel;
import org.origami.common.core.utils.Assert;
import org.origami.common.core.utils.BeanUtils;
import org.origami.system.dto.CreateUserRequest;
import org.origami.system.dto.LoginUser;
import org.origami.system.dto.SysUserDTO;
import org.origami.system.entity.SysUserDO;
import org.origami.system.mapper.SysUserMapper;
import org.origami.system.service.SysUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author origami
 * @date 2022/1/11 23:09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserDO> implements SysUserService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean save(SysUserDO entity) {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        boolean usernameExists = existByCondition(entity, SysUserDO::getUsername);
        Assert.isTrue(!usernameExists, "用户名已存在");
        boolean phoneExists = existByCondition(entity, SysUserDO::getPhone);
        Assert.isTrue(!phoneExists, "手机号码已存在");

        entity.setLastLoginTime(LocalDateTime.now());

        return super.save(entity);
    }

    @Override
    public LoginUser getLoginUserByUsername(String username) {
        return Optional.ofNullable(username).map(name -> baseMapper.selectLoginUserByUsername(name)).orElse(null);
    }

    @Override
    public LoginUser getLoginUserByEmail(String email) {
        return null;
    }

    @Override
    public void updateUserPassword(Long userId, String oldPassword, String newPassword) {

    }

    @Override
    public LoginUser getLoginUserByPhone(String phone) {
        return Optional.ofNullable(phone).map(it -> baseMapper.selectLoginUserByPhone(it)).orElse(null);
    }

    @Override
    public IPage<SysUserDTO> pageByCondition(PageModel<SysUserDTO> pageModel) {
        return super.page(pageModel.converter(SysUserDO.class))
            .map((entity) -> BeanUtils.copyProperties(entity, SysUserDTO.class));
    }

    @Override
    public SysUserDTO findById(Long id) {
        return BeanUtils.copyProperties(getById(id), SysUserDTO.class);
    }

    @Override
    public Boolean save(CreateUserRequest form) {
        SysUserDO entity = BeanUtils.copyProperties(form, SysUserDO.class);
        return save(entity);
    }

    @Override
    public Boolean updateUserDetail(SysUserDTO sysUserParam) {
        SysUserDO sysUserDO = new SysUserDO();
        BeanUtils.copyProperties(sysUserParam, sysUserDO);

        boolean usernameExists = existByCondition(sysUserDO, SysUserDO::getUsername);
        Assert.isTrue(!usernameExists, "用户名已存在");
        boolean phoneExists = existByCondition(sysUserDO, SysUserDO::getPhone);
        Assert.isTrue(!phoneExists, "手机号码已存在");

        return updateById(sysUserDO);
    }

    @Override
    public SysUserDTO getUserByUsername(String username) {
        return Optional.ofNullable(username)
            .map(s -> baseMapper.selectOne(new LambdaQueryWrapper<SysUserDO>().eq(SysUserDO::getUsername, s)))
            .map(user -> BeanUtils.copyProperties(user, SysUserDTO.class)).orElse(null);
    }

    @Override
    public SysUserDTO getUserByPhone(String phone) {
        return Optional.ofNullable(phone)
            .map(s -> baseMapper.selectOne(new LambdaQueryWrapper<SysUserDO>().eq(SysUserDO::getPhone, s)))
            .map(user -> BeanUtils.copyProperties(user, SysUserDTO.class)).orElse(null);
    }

    private boolean existByCondition(SysUserDO sysUserDO, SFunction<SysUserDO, String> function) {
        String condition = function.apply(sysUserDO);

        if (condition == null) {
            return false;
        }

        Wrapper<SysUserDO> queryWrapper = new LambdaQueryWrapper<SysUserDO>().eq(function, condition);

        Long existsCount = baseMapper.selectCount(queryWrapper);

        return existsCount > 0;
    }

}
