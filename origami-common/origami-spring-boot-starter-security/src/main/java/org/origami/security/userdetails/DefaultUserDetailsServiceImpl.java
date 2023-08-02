package org.origami.security.userdetails;

import javax.annotation.Resource;

import org.origami.common.core.utils.Assert;
import org.origami.security.common.MemberDetails;
import org.origami.system.dto.LoginUser;
import org.origami.system.service.SysUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import cn.hutool.core.util.PhoneUtil;

/**
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-21 14:35
 */
public class DefaultUserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private SysUserService sysUserService;

    /**
     * todo 通过邮箱获取
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Assert.notBlank(username, "用户名或手机号码不能为空");

        LoginUser loginUser;

        if (PhoneUtil.isMobile(username)) {
            loginUser = getLoginUserByPhone(username);
        } else {
            loginUser = sysUserService.getLoginUserByUsername(username);
        }

        if (loginUser == null) {
            throw new UsernameNotFoundException("账号不存在");
        }

        return new MemberDetails(loginUser.getId(), loginUser.getUsername(), loginUser.getPhone(), loginUser.getPassword(),
            loginUser.getEnabled(), loginUser.getRoles());

    }


    /**
     * 通过电话号码获取用户
     *
     * @param phone 电话
     * @return {@code LoginUser}
     */
    private LoginUser getLoginUserByPhone(String phone) {
        return sysUserService.getLoginUserByPhone(phone);
    }

    private LoginUser getAuthUserByEmail(String email) {
        return null;
    }
}
