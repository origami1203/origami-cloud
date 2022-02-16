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
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

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
    @Autowired
    private AuthenticationManager authenticationManager;
    // @Autowired
    // private UserDetailsService userDetailsService;
    
    
    /**
     * oauth2客户端配置
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // 数据库方式
        clients.withClientDetails(clientDetailsService());
        // 内存方式
        // clients.inMemory()
        //        // 客户端id
        //        .withClient("client_1")
        //        // 客户端密码，要加密,不然一直要求登录
        //        .secret(passwordEncoder.encode("123456"))
        //        // 资源id, 如商品资源
        //        .resourceIds("product-server")
        //        // 授权类型, 可同时支持多种授权类型
        //        .authorizedGrantTypes("authorization_code", "password", "implicit","client_credentials","refresh_token")
        //        // 授权范围标识，哪部分资源可访问（all是标识，不是代表所有）
        //        .scopes("all")
        //        // false 跳转到授权页面手动点击授权，true 不用手动授权，直接响应授权码，
        //        .autoApprove(true)
        //        .redirectUris("http://www.baidu.com/");
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // 密码授权方式需要
                .authenticationManager(authenticationManager)
                //授权码模式服务(若使用)
                .authorizationCodeServices(authorizationCodeServices())
                .tokenServices(tokenServices())
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
    
    @Bean
    public ClientDetailsService clientDetailsService() {
        JdbcClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        clientDetailsService.setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }

    /**
     * token的持久化,有内存,数据库,redis,jwt等方式
     */
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }


    /**
     * 令牌服务,创建令牌,刷新令牌,令牌增强,加密,过期等,持久化令牌使用TokenStore代理
     * @return tokenServices
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        // 令牌持久化
        tokenServices.setTokenStore(tokenStore());
        // 客户端详情
        tokenServices.setClientDetailsService(clientDetailsService());
        // 支持刷新token
        tokenServices.setSupportRefreshToken(true);
        // token和refresh_token的有效时间
        tokenServices.setAccessTokenValiditySeconds(7200);
        tokenServices.setRefreshTokenValiditySeconds(259200);
        return tokenServices;
    }

    /**
     * 使用授权码服务时需要配置
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        //基于内存存储的的授权码服务
        //return new InMemoryAuthorizationCodeServices();
        //基于内存存储的的授权码服务
        return new JdbcAuthorizationCodeServices(dataSource);
    }
    
}
