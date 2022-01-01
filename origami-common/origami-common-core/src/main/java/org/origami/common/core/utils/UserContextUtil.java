package org.origami.common.core.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * 用户信息
 *
 * @author origami
 * @date 2021/12/30 22:34
 */
@UtilityClass
public class UserContextUtil {
    
    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Objects.nonNull(authentication) ? authentication.getName() : "null";
    }
    
    // TODO
    public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return null;
    }
}
