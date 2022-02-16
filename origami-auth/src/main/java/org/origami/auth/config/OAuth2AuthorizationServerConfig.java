package org.origami.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

/**
 * 认证服务器配置
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-21 14:24
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    
    @Autowired
    private DataSource dataSource;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenStore tokenStore;
    // @Autowired
    // private AuthenticationManager authenticationManager;
    // @Autowired
    // private UserDetailsService userDetailsService;
    
    
    /**
     * oauth2客户端配置
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // clients.withClientDetails(clientDetailsService());
        // 使用内存方式
        clients.inMemory()
               // 客户端id
               .withClient("wj-pc")
               // 客户端密码，要加密,不然一直要求登录
               .secret(passwordEncoder.encode("wj-secret"))
               // 资源id, 如商品资源
               .resourceIds("product-server")
               // 授权类型, 可同时支持多种授权类型
               .authorizedGrantTypes("authorization_code", "password", "implicit","client_credentials","refresh_token")
               // 授权范围标识，哪部分资源可访问（all是标识，不是代表所有）
               .scopes("all")
               // false 跳转到授权页面手动点击授权，true 不用手动授权，直接响应授权码，
               .autoApprove(false)
               .redirectUris("http://www.baidu.com/");
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // 密码授权方式需要
                // .authenticationManager(authenticationManager)
                .tokenServices(tokenServices())
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
    
    // @Bean
    // public ClientDetailsService clientDetailsService() {
    //     JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
    //     clientDetailsService.setPasswordEncoder(passwordEncoder);
    //     return clientDetailsService;
    // }
    
    
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setClientDetailsService(new JdbcClientDetailsService(dataSource));
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setAccessTokenValiditySeconds(7200);
        tokenServices.setRefreshTokenValiditySeconds(259200);
        return tokenServices;
    }
    
}
