package org.origami.common.security.service;

import lombok.RequiredArgsConstructor;
import org.origami.common.core.base.R;
import org.origami.upm.api.dto.SysAuthUserDTO;
import org.origami.upm.api.feign.RemoteSysUserService;
import org.springframework.core.Ordered;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-21 14:35
 */
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService, Ordered {

    private final static String ROLE_PREFIX = "ROLE_";
    private final RemoteSysUserService sysUserFeignClient;

    /**
     * todo 通过电话和邮箱获取用户
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        R<SysAuthUserDTO> result = sysUserFeignClient.getAuthUserByUsername(username);

        if (result == null || result.getData() == null) {
            throw new UsernameNotFoundException("账号不存在");
        }

        SysAuthUserDTO authUser = result.getData();

        Set<GrantedAuthority> roles = authUser.getRoles().stream().map(it -> new SimpleGrantedAuthority(ROLE_PREFIX + it)).collect(Collectors.toSet());

        return new User(authUser.getUsername(), authUser.getPassword(), authUser.getEnabled(), true, true, true, roles);

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
