package org.origami.auth.controller;

import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.SneakyThrows;
import org.origami.auth.api.dto.AuthenticationVO;
import org.origami.auth.api.dto.LoginParam;
import org.origami.common.core.base.Result;
import org.origami.common.core.utils.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-02-16 13:05
 */
@RestController
public class AuthController {
    
    @Autowired
    private RestTemplate restTemplate;
    
    @PostMapping("/login")
    @SneakyThrows
    public Result<AuthenticationVO> login(@RequestBody LoginParam loginParam) {
        // MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        // paramMap.add("username", loginParam.getUsername());
        // paramMap.add("password", loginParam.getPassword());
        // paramMap.add("client_id", "client_1");
        // paramMap.add("client_secret", "123456");
        // paramMap.add("grant_type", "password");
        //
        // HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //
        // HttpEntity<MultiValueMap<String, String>> request =
        //         new HttpEntity<MultiValueMap<String, String>>(paramMap, headers);
        //
        // ResponseEntity<AuthenticationVO> result =
        //         restTemplate.postForEntity("http://localhost/oauth/token", request,
        //                                    AuthenticationVO.class);
    
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", loginParam.getUsername());
        paramMap.put("password", loginParam.getPassword());
        paramMap.put("client_id", "client_1");
        paramMap.put("client_secret", "123456");
        paramMap.put("grant_type", "password");
    
    
        String result = HttpUtil.post("http://localhost/oauth/token", paramMap);
    
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    
        AuthenticationVO authenticationVO =
                objectMapper.readValue(result, AuthenticationVO.class);
    
    
        return Result.ok(authenticationVO);
    
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
}
