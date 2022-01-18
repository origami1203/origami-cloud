package org.origami.common.swagger.boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 属性
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-07 08:58
 */
@Data
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    /**
     * 是否开启swagger
     */
    private Boolean enabled;
    
    /**
     * swagger会解析的包路径
     **/
    private String basePackage = "";
    
    /**
     * swagger会解析的url规则
     **/
    private List<String> basePath = new ArrayList<>();
    
    /**
     * 在basePath基础上需要排除的url规则
     **/
    private List<String> excludePath = new ArrayList<>();
    
    /**
     * 标题
     **/
    private String title = "Swagger接口";
    
    /**
     * 描述
     **/
    private String description = "";
    
    /**
     * 版本
     **/
    private String version = "1.0.0";
    
    /**
     * 许可证
     **/
    private String license = "";
    
    /**
     * 许可证URL
     **/
    private String licenseUrl = "#";
    
    /**
     * 服务条款URL
     **/
    private String termsOfServiceUrl = "#";
    
    /**
     * 联系人信息
     */
    private Contact contact = new Contact();

    @Data
    public class Contact {
        private String name;
        private String url;
        private String email;
    }

    
}
