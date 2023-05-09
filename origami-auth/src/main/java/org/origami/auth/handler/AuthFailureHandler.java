package org.origami.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.origami.common.core.base.CodeEnum;
import org.origami.common.core.base.R;
import org.origami.common.core.utils.JacksonUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 失败操作,密码错误,账号过期等
 *
 * @author origami
 * @date 2022/1/21 21:43
 */
@Slf4j
public class AuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException,
                                                                                  ServletException {
        log.debug("登录失败: {}", exception.getMessage());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter writer = response.getWriter();
        
        if (exception instanceof BadCredentialsException) {
            writer.write(JacksonUtil.toJson(R.failed(CodeEnum.USER_BAD_CREDENTIALS)));
            return;
        }
        if (exception instanceof DisabledException) {
            writer.write(JacksonUtil.toJson(R.failed(CodeEnum.USER_ACCOUNT_DISABLED)));
            return;
        }
        writer.write(JacksonUtil.toJson(R.failed("登录失败")));
        
        
    }
}
