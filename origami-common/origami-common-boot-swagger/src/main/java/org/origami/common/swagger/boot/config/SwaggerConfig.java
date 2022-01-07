package org.origami.common.swagger.boot.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

/**
 * 配置
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-07 08:56
 */
@Configuration
@EnableOpenApi
@ConditionalOnWebApplication
// @EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(name = "swagger.enabled", matchIfMissing = true)
public class SwaggerConfig {

    /**
     * 默认的排除路径，排除Spring Boot默认的错误处理路径和端点
     */
    private static final List<String> DEFAULT_EXCLUDE_PATH = Arrays.asList("/error", "/actuator/**");

    private static final String BASE_PATH = "/**";

    @Bean
    public Docket api(SwaggerProperties properties) {

        if (properties.getBasePath().isEmpty()) {
            properties.getBasePath().add(BASE_PATH);
        }
        properties.getExcludePath().addAll(DEFAULT_EXCLUDE_PATH);

        ApiSelectorBuilder builder = new Docket(DocumentationType.OAS_30)    // 3.0版本
                .apiInfo(apiInfo(swaggerProperties()))
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()));

        properties.getBasePath().stream().map(PathSelectors::ant).forEach(builder::paths);
        properties.getExcludePath().stream().map(excludePath -> PathSelectors.ant(excludePath).negate()).forEach(builder::paths);

        return builder.build();
    }

    @Bean
    public ApiInfo apiInfo(SwaggerProperties properties) {
        return new ApiInfoBuilder()
                .title(properties.getTitle())
                .description(properties.getDescription())
                .version(properties.getVersion())
                .contact(new Contact(properties.getContact().getName(), properties.getContact().getUrl(), properties.getContact().getEmail()))
                .license(properties.getLicense())
                .licenseUrl(properties.getLicenseUrl())
                .termsOfServiceUrl(properties.getTermsOfServiceUrl())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean(SwaggerProperties.class)
    public SwaggerProperties swaggerProperties() {
        return new SwaggerProperties();
    }

}
