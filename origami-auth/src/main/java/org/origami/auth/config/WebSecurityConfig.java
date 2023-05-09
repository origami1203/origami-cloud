package org.origami.auth.config;

import lombok.RequiredArgsConstructor;
import org.origami.auth.handler.AuthAccessDeniedHandler;
import org.origami.auth.handler.AuthEntryPoint;
import org.origami.auth.handler.AuthFailureHandler;
import org.origami.auth.handler.success.SimpleAbstractTokenAuthSuccessHandler;
import org.origami.upm.api.feign.RemoteSysUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author origami1203
 * @date 2021-12-23 21:25
 * @description TODO
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final RemoteSysUserService remoteSysUserService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.formLogin().loginPage("/login").loginProcessingUrl("/login");
        http.authorizeRequests()
            .antMatchers("/login").permitAll();
        http.cors()
            .and()
            .exceptionHandling()
            // .authenticationEntryPoint(authenticationEntryPoint())
            .accessDeniedHandler(accessDeniedHandler())
            .and()
            .authorizeRequests()
            .anyRequest().authenticated();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
           .antMatchers("/**/*.css",
                        "/**/*.js",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                        "/swagger-ui/*",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/webjars/**");
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode(
                    "123456"))
            .roles("ADMIN")
            .and()
            .withUser("ls").password(passwordEncoder().encode("1234"))
            .roles("USER");
    }
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new SimpleAbstractTokenAuthSuccessHandler();
    }
    
    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new AuthFailureHandler();
    }
    
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AuthEntryPoint();
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AuthAccessDeniedHandler();
    }
    
    // @Bean
    // @Override
    // public UserDetailsService userDetailsService() {
    //     return new MemberDetailsService(remoteSysUserService);
    // }
    
}
