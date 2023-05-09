package org.origami.common.security.handler;

import cn.hutool.core.collection.CollUtil;
import org.origami.upm.api.feign.RemoteSysRoleService;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 查询数据库,通过url获取访问该url所需要的权限
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-02-16 14:23
 */
public class UrlFilterInvocationSecurityMetadataSource implements
                                                       FilterInvocationSecurityMetadataSource {
    
    
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Resource
    private RemoteSysRoleService sysRoleFeignClient;
    
    // 获取安全对象object所需要的权限的集合
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws
                                                                    IllegalArgumentException {
        
        Set<ConfigAttribute> needRoles = new HashSet<>();
        
        FilterInvocation filterInvocation = (FilterInvocation) object;
        
        HttpServletRequest httpRequest = filterInvocation.getHttpRequest();
        String uri = httpRequest.getRequestURI();
        
        Map<String, Set<String>> permRoleMap =
                sysRoleFeignClient.getPermissionRoleMap().getData();
        
        permRoleMap.forEach((perms, roles) -> {
            if (antPathMatcher.match(perms, uri)) {
                needRoles.addAll(roles.stream()
                                      .map(role -> new SecurityConfig("ROLE_" + role))
                                      .collect(Collectors.toSet()));
            }
        });
        
        // 如果url没有配置，默认该url需要拥有user权限
        if (CollUtil.isEmpty(needRoles)) {
            needRoles.add(new SecurityConfig("ROLE_USER"));
        }
        
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
}
