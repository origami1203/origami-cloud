package org.origami.auth.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;

/**
 * UserDetails的实现类,Authentication的getPrincipal()方法一般会返回此对象
 *
 * @author origami
 * @version 1.0.0
 * @date 2022-01-21 14:40
 */
@Getter
@Setter
public class AuthUser extends User {
    
    private Long id;
    private Long deptId;
    
    public AuthUser(Long id,
                    Long deptId,
                    String username,
                    String password,
                    boolean enabled,
                    Collection<GrantedAuthority> roles) {
        super(username, password, enabled, true, true, true, roles);
        this.id = id;
        this.deptId = deptId;
    }
    
}
