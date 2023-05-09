package org.origami.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.InMemoryClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;
import java.util.Collections;

/**
 * 认证服务器配置
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-21 14:24
 */
@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    
    private final TokenEnhancer tokenEnhancer;
    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    private final TokenStore tokenStore;
    private final AuthenticationManager authenticationManager;
    // private final UserDetailsService userDetailsService;
    // private final ClientDetailsService clientDetailsService;
    private final AuthorizationCodeServices authorizationCodeServices;
    private AuthorizationServerTokenServices tokenServices;
    
    
    /**
     * oauth2客户端配置
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 数据库方式
        // clients.withClientDetails(clientDetailsService);
        // 内存方式
        clients.inMemory()
               // 客户端id
               .withClient("app1")
               // 客户端密码，要加密,不然一直要求登录
               .secret(passwordEncoder.encode("123456"))
               // 资源id, 如商品资源
               .resourceIds("product-server")
               // 授权类型, 可同时支持多种授权类型
               .authorizedGrantTypes("authorization_code", "password", "refresh_token")
               // 授权范围标识，哪部分资源可访问（all是标识，不是代表所有）
               .scopes("all")
               // false 跳转到授权页面手动点击授权，true 不用手动授权，直接响应授权码，
               .autoApprove(true)
               .redirectUris("http://www.baidu.com");
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // 密码授权方式需要
                .authenticationManager(authenticationManager)
                //授权码模式服务(若使用)
                .authorizationCodeServices(authorizationCodeServices)
                .tokenServices(tokenServices)
                // 重用刷新token
                .reuseRefreshTokens(true)
                // .userDetailsService(userDetailsService)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                // .checkTokenAccess("isAuthenticated()")
                // 允许client表单认证
                .allowFormAuthenticationForClients();
    }
    
    
    
}
