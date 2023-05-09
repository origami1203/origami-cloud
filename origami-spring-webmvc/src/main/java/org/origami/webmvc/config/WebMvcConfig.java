package org.origami.webmvc.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.origami.common.core.data.query.PageQuery;
import org.origami.common.core.utils.JacksonUtil;
import org.origami.webmvc.handler.PageQueryHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;
import java.util.List;

/**
 * web mvc配置
 *
 * @author origami
 * @date 2022/1/14 22:21
 */
@Slf4j
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


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PageQueryHandlerMethodArgumentResolver());
    }

    @Bean
    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        // 配置ObjectMapper
        ObjectMapper mapper = JacksonUtil.getObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addDeserializer(PageQuery.class, new JsonDeserializer<PageQuery>() {
            @Override
            public  PageQuery deserialize(JsonParser p, DeserializationContext ctxt) throws
                                                                                    IOException,
                                                                                    JsonProcessingException {
                String pageSize = p.getValueAsString("pageSize");
                String valueAsString = p.getValueAsString();
                return null;
            }
        });
        mapper.registerModule(simpleModule);
        return new MappingJackson2HttpMessageConverter(mapper);
    }
}
