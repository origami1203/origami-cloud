package org.origami.common.core.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

/**
 * 用户信息
 *
 * @author origami
 * @date 2021/12/30 22:34
 */
public abstract class UserContextUtil {
    
    private UserContextUtil() {
        throw new UnsupportedOperationException(
                "This is a utility class and cannot be instantiated");
    }
    
    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Objects.nonNull(authentication) ? authentication.getName() : "anonymous";
    }
    
    // TODO
    public static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return null;
    }
}
