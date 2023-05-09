package org.origami.common.swagger.boot.config;

import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
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
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
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
@Slf4j
@EnableOpenApi
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(prefix = "swagger", name = "enabled", havingValue = "true")
public class SwaggerConfig {
    /**
     * 默认的排除路径，排除Spring Boot默认的错误处理路径和端点
     */
    private static final List<String> DEFAULT_EXCLUDE_PATH =
            Arrays.asList("/error", "/actuator/**");
    private static final String BASE_PATH = "/**";

    @Bean
    public Docket api(SwaggerProperties properties) {

        log.info("开启swagger-ui");

        if (properties.getBasePath().isEmpty()) {
            properties.getBasePath().add(BASE_PATH);
        }
        properties.getExcludePath().addAll(DEFAULT_EXCLUDE_PATH);
        // 3.0版本
        ApiSelectorBuilder builder = new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo(properties))
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getBasePackage()));

        properties.getBasePath().stream().map(PathSelectors::ant).forEach(builder::paths);
        properties.getExcludePath()
                  .stream()
                  .map(excludePath -> PathSelectors.ant(excludePath).negate())
                  .forEach(builder::paths);

        builder.build().securityContexts(Arrays.asList(securityContexts()))
               .securitySchemes(Collections.singletonList(securitySchemes()));

        return builder.build();
    }

    @Bean
    public ApiInfo apiInfo(SwaggerProperties properties) {
        return new ApiInfoBuilder()
                .title(properties.getTitle())
                .description(properties.getDescription())
                .version(properties.getVersion())
                .contact(new Contact(properties.getContact().getName(),
                                     properties.getContact().getUrl(),
                                     properties.getContact().getEmail()))
                .license(properties.getLicense())
                .licenseUrl(properties.getLicenseUrl())
                .termsOfServiceUrl(properties.getTermsOfServiceUrl())
                .build();
    }

    @Bean
    public SwaggerWebMvcConfigurer swaggerUiConfigurer() {
        return new SwaggerWebMvcConfigurer();
    }

    @Bean
    public SwaggerBannerConfig swaggerBannerConfig() {
        return new SwaggerBannerConfig();
    }

    private SecurityScheme securitySchemes() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    private SecurityContext securityContexts() {
        return SecurityContext.builder()
                              .securityReferences(defaultAuth())
                              .forPaths(PathSelectors.any())
                              .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("xxx", "描述信息");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
    }

}
