package org.origami.common.security.handler;

import lombok.RequiredArgsConstructor;
import org.origami.upm.api.feign.SysRoleFeignClient;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 查询数据库,通过url获取访问该url所需要的权限
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-02-16 14:23
 */
@RequiredArgsConstructor
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final SysRoleFeignClient sysRoleFeignClient;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation filterInvocation = (FilterInvocation) object;

        Map<String, Set<String>> permRoleMap = sysRoleFeignClient.getPermissionRoleMap().getData();
        return null;
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
