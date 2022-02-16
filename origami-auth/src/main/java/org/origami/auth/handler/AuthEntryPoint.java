package org.origami.auth.handler;

import org.origami.common.core.base.Code;
import org.origami.common.core.base.Result;
import org.origami.common.core.utils.JacksonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 未登陆用户访问需要认证的接口
 *
 * @author origami
 * @date 2022/1/21 21:53
 */
public class AuthEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException,
                                                                       ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        PrintWriter out = response.getWriter();
        
        out.write(JacksonUtil.toJson(Result.failed(Code.USER_NOT_LOGIN)));
    }
}
