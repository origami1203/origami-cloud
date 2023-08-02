package org.origami.webmvc;

import java.util.Arrays;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.HibernateValidator;
import org.origami.common.core.utils.JsonUtil;
import org.origami.webmvc.handler.GlobalExceptionHandler;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author origami
 * @date 2022/1/27 20:23
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(ValidationAutoConfiguration.class)
@Import(GlobalExceptionHandler.class)
public class WebMvcAutoConfiguration {

    @Configuration(proxyBeanMethods = false)
    public static class WebMvcConfig implements WebMvcConfigurer {

        //@Override
        //public void addCorsMappings(CorsRegistry registry) {
        //    CorsRegistration corsRegistration = registry.addMapping("/**");
        //    corsRegistration.allowedOriginPatterns("*");
        //    corsRegistration.allowedMethods("*");
        //    corsRegistration.allowCredentials(true);
        //}

        @Bean
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
            // 配置ObjectMapper
            ObjectMapper mapper = JsonUtil.getObjectMapper();
            return new MappingJackson2HttpMessageConverter(mapper);
        }
    }

    @Configuration
    public static class ValidationConfig {

        /**
         * 设置validation failFast
         */
        @Bean
        public Validator validator(AutowireCapableBeanFactory springFactory) {
            return Validation.byProvider(HibernateValidator.class).configure().failFast(true)
                .constraintValidatorFactory(new SpringConstraintValidatorFactory(springFactory)).buildValidatorFactory()
                .getValidator();
        }
    }

    /**
     * 过滤器配置跨域，通过拦截器配置，使用security时，过滤器直接返回，没有经过拦截器，跨域无法生效
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setMaxAge(1800L);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        FilterRegistrationBean<CorsFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new CorsFilter(urlBasedCorsConfigurationSource));
        filterRegistrationBean.setOrder(-100);
        return filterRegistrationBean;
    }

}
