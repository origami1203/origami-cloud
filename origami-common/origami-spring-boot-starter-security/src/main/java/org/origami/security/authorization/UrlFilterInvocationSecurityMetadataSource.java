package org.origami.security.authorization;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.AllArgsConstructor;
import org.origami.common.core.utils.Assert;
import org.origami.security.common.SecurityConstants;
import org.origami.security.config.properties.SecurityProperties;
import org.origami.system.dto.SysPermissionRoleDTO;
import org.origami.system.service.SysPermissionService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import lombok.Data;

/**
 * 查询数据库,通过url获取访问该url所需要的权限
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-02-16 14:23
 */
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static final String ALL_METHOD_ALLOWED = "*";
    private static final String ALL_URL_ALLOWED = "/**";
    private static final String ALLOW_ALL = "/**:*";
    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();
    @Resource
    private SysPermissionService sysPermissionService;
    @Resource
    private SecurityProperties securityProperties;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        FilterInvocation filterInvocation = (FilterInvocation)object;

        HttpServletRequest httpRequest = filterInvocation.getHttpRequest();
        String requestUri = httpRequest.getRequestURI();
        String requestMethod = httpRequest.getMethod();

        for (String ignoreUrl : securityProperties.getIgnoreList()) {
            if (ANT_PATH_MATCHER.match(ignoreUrl, requestUri)) {
                return Collections.emptySet();
            }
        }

        // todo 缓存
        List<SysPermissionRoleDTO> permissionRoles = sysPermissionService.getPermissionRoles().stream()
            .filter(dto -> !ALLOW_ALL.equals(dto.getUrl())).collect(Collectors.toList());

        Set<ConfigAttribute> needRoles = new HashSet<>();

        permissionRoles.forEach(it -> {
            Tuple<String, String> urlAndMethod = extractUrlAndMethod(it.getUrl());

            String url = urlAndMethod.getFirst();
            String method = urlAndMethod.getSecond();

            if (isAllMatched(requestUri, requestMethod, url, method)) {
                needRoles.addAll(it.getRoles().stream().map(sysRole -> new SecurityConfig(sysRole.getRoleCode()))
                    .collect(Collectors.toSet()));
            }

        });

        // 我们配置超级管理员拥有/**权限，那么未配置的url
        if (needRoles.isEmpty()) {
            return SecurityConfig.createList(SecurityConstants.DEFAULT_LOGIN_ROLE_NAME,
                SecurityConstants.DEFAULT_SUPER_ADMIN_ROLE_NAME);
        }
        needRoles.add(new SecurityConfig(SecurityConstants.DEFAULT_SUPER_ADMIN_ROLE_NAME));

        return needRoles;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    /**
     * 提取url和method
     *
     * @param urlWithMethod 带有method的url
     * @return 第一个变量为url，第二个为对应的method
     */
    private Tuple<String, String> extractUrlAndMethod(String urlWithMethod) {
        Assert.notBlank(urlWithMethod, "url不能为空");
        String[] urlMethodArr = urlWithMethod.split(":");
        if (urlMethodArr.length > 1) {
            return new Tuple<>(urlMethodArr[0], urlMethodArr[1]);
        }

        return new Tuple<>(urlMethodArr[0], ALL_METHOD_ALLOWED);
    }

    private boolean isAllMatched(String requestUri, String requestMethod, String url, String method) {
        return isMethodMatched(requestMethod, method) && ANT_PATH_MATCHER.match(url, requestUri);
    }

    private boolean isMethodMatched(String requestMethod, String method) {
        return ALL_METHOD_ALLOWED.equals(method) || requestMethod.equalsIgnoreCase(method);
    }

    @Data
    @AllArgsConstructor
    private class Tuple<F, S> {
        private final F first;
        private final S second;
    }
}
