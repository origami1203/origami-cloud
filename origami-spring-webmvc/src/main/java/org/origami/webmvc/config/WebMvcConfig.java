package org.origami.webmvc.config;

import lombok.extern.slf4j.Slf4j;
import org.origami.common.core.utils.JacksonUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * web mvc配置
 *
 * @author origami
 * @date 2022/1/14 22:21
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 配置跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 所有请求
        registry.addMapping("/**")
                // 允许的方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 允许任意其他源跨域请求
                .allowedOrigins("*")
                .maxAge(3600);
    }
    
    /**
     * 配置localDateTime转换，long转string
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        
        converters.add(mappingJackson2HttpMessageConverter());
    }
    
    @Bean
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter =
                new MappingJackson2HttpMessageConverter();
        // JacksonUtil已经配置ObjectMapper
        jackson2HttpMessageConverter.setObjectMapper(JacksonUtil.getObjectMapper());
        return jackson2HttpMessageConverter;
    }
}
