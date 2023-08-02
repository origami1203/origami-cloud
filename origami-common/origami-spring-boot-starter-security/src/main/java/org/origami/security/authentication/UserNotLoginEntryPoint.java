package org.origami.security.authentication;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.origami.common.core.base.CodeEnum;
import org.origami.common.core.base.R;
import org.origami.common.core.utils.JsonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import lombok.extern.slf4j.Slf4j;

/**
 * 未登陆用户访问需要认证的接口
 *
 * @author origami
 * @date 2022/1/21 21:53
 */
@Slf4j
public class UserNotLoginEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
        log.debug("用户尚未登陆");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(JsonUtil.toJson(R.failed(CodeEnum.USER_NOT_LOGIN)));
        out.flush();
    }
}
