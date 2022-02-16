package org.origami.auth.handler;

import org.origami.common.core.base.Code;
import org.origami.common.core.base.Result;
import org.origami.common.core.utils.JacksonUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 权限不足处理器
 *
 * @author origami
 * @date 2022/1/28 22:40
 */
public class AuthAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException,
                                                                           ServletException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        
        PrintWriter writer = response.getWriter();
        
        writer.write(JacksonUtil.toJson(Result.failed(Code.NO_PERMISSION)));
        
    }
}
