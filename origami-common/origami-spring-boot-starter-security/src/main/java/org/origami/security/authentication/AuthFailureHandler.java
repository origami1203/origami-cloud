package org.origami.security.authentication;

import lombok.extern.slf4j.Slf4j;
import org.origami.common.core.base.CodeEnum;
import org.origami.common.core.base.R;
import org.origami.common.core.utils.JsonUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 失败操作
 *
 * @author origami
 * @date 2022/1/21 21:43
 */
@Slf4j
public class AuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();

        if (exception instanceof BadCredentialsException) {
            if (log.isDebugEnabled()) {
                log.debug("用户密码错误,认证失败");
            }
            writer.write(JsonUtil.toJson(R.failed(CodeEnum.USER_BAD_CREDENTIALS)));
            writer.flush();
            return;
        }

        writer.write(JsonUtil.toJson(R.failed(exception.getMessage())));
        writer.flush();

    }
}
