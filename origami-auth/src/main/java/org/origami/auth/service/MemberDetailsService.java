//package org.origami.auth.service;
//
//import lombok.RequiredArgsConstructor;
//import org.origami.common.core.base.R;
//import org.origami.upm.dto.LoginUser;
//import org.origami.upm.api.service.RemoteSysUserService;
//import org.springframework.core.Ordered;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// * @author origami
// * @version 1.0.0
// * @date 2022-01-21 14:35
// */
//@RequiredArgsConstructor
//public class MemberDetailsService implements UserDetailsService, Ordered {
//
//    private final static String ROLE_PREFIX = "ROLE_";
//    private final RemoteSysUserService remoteSysUserService;
//
//    /**
//     * todo 通过电话和邮箱获取用户
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        R<LoginUser> result = remoteSysUserService.getAuthUserByUsername(username);
//
//        if (result == null || result.getData() == null) {
//            throw new UsernameNotFoundException("账号不存在");
//        }
//
//        LoginUser userDTO = result.getData();
//
//        Set<GrantedAuthority> roles = userDTO.getRoles()
//                                             .stream()
//                                             .map(it -> new SimpleGrantedAuthority(
//                                                     ROLE_PREFIX + it))
//                                             .collect(Collectors.toSet());
//
//        return new AuthUser(userDTO.getId(), userDTO.getDeptId(), userDTO.getUsername(),
//                            userDTO.getPassword(),
//                            userDTO.getEnabled(), roles);
//
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
