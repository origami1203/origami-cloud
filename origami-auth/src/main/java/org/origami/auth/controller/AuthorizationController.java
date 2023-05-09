package org.origami.auth.controller;

import com.google.common.collect.Maps;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.origami.auth.api.dto.AuthenticationVO;
import org.origami.auth.api.dto.LoginParam;
import org.origami.common.core.base.R;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 *
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-02-16 13:05
 */
@Slf4j
@Controller
@SessionAttributes("authorizationRequest")
public class AuthorizationController {
    
    @Resource
    private RestTemplate restTemplate;
    
    
    /**
     * oauth2密码模式登陆接口,直接返回token
     *
     * @param loginParam     登录参数
     * @param servletRequest servlet请求
     * @return {@code R<AuthenticationVO>}
     */
    @ResponseBody
    @PostMapping("/login")
    public R<AuthenticationVO> login(@RequestBody LoginParam loginParam,
                                     HttpServletRequest servletRequest) {
        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("username", loginParam.getUsername());
        paramMap.add("password", loginParam.getPassword());
        paramMap.add("client_id", "app1");
        paramMap.add("client_secret", "123456");
        paramMap.add("grant_type", "password");
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        HttpEntity<MultiValueMap<String, String>> request =
                new HttpEntity<MultiValueMap<String, String>>(paramMap, headers);
        
        ResponseEntity<AuthenticationVO> result;
        try {
            result = restTemplate.postForEntity("http://localhost:10086/oauth/token",
                                                request,
                                                AuthenticationVO.class);
        } catch (Exception e) {
            log.info("获取token出错:{}", e.getMessage());
            if (e.getMessage().contains("Bad credentials")) {
                return R.failed("账号或密码错误");
            }
            return R.failed(e.getMessage());
        }
        
        AuthenticationVO body = result.getBody();
        return R.ok(body);
    }
    
    // 自定义跳转登录页
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
    
    /**
     * 授权界面
     *
     * @param model   模型
     * @param request 请求
     * @return {@code ModelAndView}
     */
    @RequestMapping("/oauth/confirm_access")
    public ModelAndView confirm(Map<String, Object> model, HttpServletRequest request) {
        AuthorizationRequest authorizationRequest =
                (AuthorizationRequest) model.get("authorizationRequest");
        ModelAndView modelView = new ModelAndView("confirm");
        modelView.addObject("clientId", authorizationRequest.getClientId());
        modelView.addObject("scopes", authorizationRequest.getScope());
        return modelView;
    }
    
    @ApiOperation(value = "授权服务异常跳转页面")
    @GetMapping("/oauth/error")
    public ModelAndView error(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        Object error = request.getAttribute("error");
        String errorMsg;
        if (error instanceof OAuth2Exception) {
            OAuth2Exception oauthError = (OAuth2Exception) error;
            errorMsg = oauthError.getMessage();
        } else {
            errorMsg = "未知异常";
        }
        model.put("errorMsg", errorMsg);
        return new ModelAndView("error", model);
    }
    
    // @RequestMapping("/callback")
    // public String code(String code) {
    //     MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
    //     paramMap.add("client_id", "app1");
    //     paramMap.add("client_secret", "123456");
    //     paramMap.add("grant_type", "password");
    //
    //     HttpHeaders headers = new HttpHeaders();
    //     headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    //
    //     HttpEntity<MultiValueMap<String, String>> request =
    //             new HttpEntity<MultiValueMap<String, String>>(paramMap, headers);
    //     restTemplate.p("http://localhost:10086/oauth/token",)
    // }
    
}
