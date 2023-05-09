package org.origami.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 28455
 */
@EnableFeignClients(basePackages = "org.origami")
@EnableDiscoveryClient
@SpringBootApplication
public class OrigamiAuthApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(OrigamiAuthApplication.class, args);
    }
    
}
