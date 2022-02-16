package org.origami.auth.controller;

import cn.hutool.http.HttpUtil;
import org.origami.auth.api.dto.LoginParam;
import org.origami.common.core.base.Result;
import org.origami.upm.api.dto.SysUserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/login")
    public Result<SysUserDTO> login(@RequestBody LoginParam loginParam) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", loginParam.getUsername());
        paramMap.put("password", loginParam.getPassword());
        paramMap.put("client_id", "client_admin");
        paramMap.put("client_secret", "123456");
        paramMap.put("grant_type", "password");
        String result = HttpUtil.post("http://localhost:10086/oauth/token", paramMap);
        System.out.println(result);
        return null;
    }

}
