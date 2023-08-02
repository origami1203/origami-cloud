package org.origami.auth.handler.success;

import org.origami.common.core.base.R;
import org.origami.common.core.utils.JsonUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证成功操作
 *
 * @author origami
 * @date 2022/1/21 21:36
 */
public abstract class AbstractTokenAuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException,
                                                                              ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();
        String token = generateAndHandleToken(authentication);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        writer.write(JsonUtil.toJson(R.ok("登陆成功", tokenMap)));
    }

    public abstract String generateAndHandleToken(Authentication auth);
}
