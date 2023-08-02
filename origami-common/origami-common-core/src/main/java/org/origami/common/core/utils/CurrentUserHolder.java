package org.origami.common.core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 类似Spring的SecurityContextHolder获取当前登录的用户信息
 *
 * @author origami
 * @date 2021/12/30 22:34
 */
public abstract class CurrentUserHolder {

    private static final String DEFAULT_ANONYMOUS_USERNAME = "AnonymousUser";
    private static final ThreadLocal<UserInfo<?>> USER_CONTEXT_HOLDER = new ThreadLocal<>();

    private CurrentUserHolder() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * 获取当前用户用户名
     *
     * @return {@code String}
     */
    public static String getUsername() {
        UserInfo<?> userInfo = USER_CONTEXT_HOLDER.get();
        if (userInfo == null) {
            userInfo = createAnonymousUser();
            USER_CONTEXT_HOLDER.set(userInfo);
        }
        return userInfo.getUsername();
    }

    /**
     * 获取用户id
     *
     * @param idClass id的类型
     * @return 用户id可能为null
     */
    @SuppressWarnings("unchecked")
    public static <T> T getUserId(Class<T> idClass) {
        UserInfo<?> userInfo = USER_CONTEXT_HOLDER.get();
        if (userInfo == null) {
            userInfo = createAnonymousUser();
            USER_CONTEXT_HOLDER.set(userInfo);
        }
        return (T)userInfo.getId();
    }

    public static void clearUser() {
        USER_CONTEXT_HOLDER.remove();
    }

    public static <T> void setUser(T id, String username) {
        Assert.nonNull(id, "id不能为null");
        Assert.notBlank(username, "username不能为空");
        USER_CONTEXT_HOLDER.set(new UserInfo<T>(id, username));
    }

    private static UserInfo<?> createAnonymousUser() {
        return new UserInfo<>(null, DEFAULT_ANONYMOUS_USERNAME);
    }

    @Data
    @AllArgsConstructor
    private static class UserInfo<T> {
        private T id;
        private String username;
    }
}
