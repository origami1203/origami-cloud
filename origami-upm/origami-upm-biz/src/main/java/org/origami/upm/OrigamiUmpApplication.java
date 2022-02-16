package org.origami.upm;

import org.origami.webmvc.EnableWebMvcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 权限管理biz
 *
 * @author origami
 * @date 2021/12/31 12:03
 */
@EnableWebMvcConfig
@SpringBootApplication
@EnableDiscoveryClient
public class OrigamiUmpApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrigamiUmpApplication.class, args);
    }
}
