package org.origami.auth.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;

/**
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-21 14:40
 */
public class AuthUser extends User {
    public AuthUser(String username, String password, boolean enabled, Collection<GrantedAuthority> roles) {
        super(username, password, enabled, true, true, true, roles);
    }


}
