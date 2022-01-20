package org.origami.auth.origamiauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author origami1203
 * @date 2021-12-23 21:25
 * @description TODO
 */
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }
}
