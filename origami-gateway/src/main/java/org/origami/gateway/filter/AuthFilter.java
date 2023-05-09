package org.origami.gateway.filter;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.origami.common.core.base.CodeEnum;
import org.origami.common.core.constant.AuthConstant;
import org.origami.gateway.config.properties.IgnoreListProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * 统一鉴权过滤器
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-02-18 12:44
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {
    
    private static final List<String> DEFAULT_WHITE_LIST = Arrays.asList("/login", "/oauth/**");
    private final IgnoreListProperties ignoreListProperties;
    private static final AntPathMatcher MATCHER = new AntPathMatcher();
    
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String url = request.getURI().getPath();
        
        
        // 白名单直接放行
        if (isIgnoredUrl(url)) {
            return chain.filter(exchange);
        }
        
        String token = getTokenFromRequest(request);
        
        if (token == null) {
            CodeEnum.USER_NOT_LOGIN.throwException();
        }
        
        // 鉴权
        
        
        
        return null;
    }
    
    @Override
    public int getOrder() {
        return 0;
    }
    
    /**
     * 从http请求中获取token
     *
     * @param request ServerHttpRequest
     * @return 如果没有token返回null
     */
    private String getTokenFromRequest(ServerHttpRequest request) {
        // 从header中获取Authorization
        String authHeaderValue = request.getHeaders().getFirst(AuthConstant.TOKEN_HEADER_KEY);
        if (StrUtil.isNotBlank(authHeaderValue)) {
            return authHeaderValue.replace(AuthConstant.TOKEN_PREFIX, "");
        }
        
        return null;
    }
    
    /**
     * 是否是需要忽略的url
     *
     * @param url 当前访问的url
     * @return boolean
     */
    private boolean isIgnoredUrl(String url) {
        List<String> whiteList =
                Optional.ofNullable(ignoreListProperties.getWhiteList())
                        .orElse(new ArrayList<>());
        whiteList.addAll(DEFAULT_WHITE_LIST);
        
        return whiteList.stream().anyMatch(it -> MATCHER.match(it, url));
    }
    
}
