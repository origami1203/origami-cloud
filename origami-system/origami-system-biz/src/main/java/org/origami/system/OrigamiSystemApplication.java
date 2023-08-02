package org.origami.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 权限管理biz
 *
 * @author origami
 * @date 2021/12/31 12:03
 */
@EnableCaching
@SpringBootApplication
public class OrigamiSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrigamiSystemApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2).apiInfo(new ApiInfoBuilder()
            // .title("swagger-bootstrap-ui-demo RESTful APIs")
            .description("# swagger-bootstrap-ui-demo RESTful APIs").termsOfServiceUrl("http://www.xx.com/")
            .contact("xx@qq.com").version("1.0").build())
            // 分组名称
            .groupName("2.X版本").select()
            // 这里指定Controller扫描包路径
            .apis(RequestHandlerSelectors.basePackage("org.origami.system.controller")).paths(PathSelectors.any()).build();
        return docket;
    }

}
