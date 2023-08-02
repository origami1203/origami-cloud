package org.origami.security.authentication;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.origami.common.core.base.R;
import org.origami.common.core.utils.JsonUtil;
import org.origami.security.common.MemberDetails;
import org.origami.security.common.SecurityConstants;
import org.origami.security.token.TokenService;
import org.origami.system.entity.SysUserDO;
import org.origami.system.service.SysUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.google.common.collect.ImmutableMap;

/**
 * 认证成功操作
 *
 * @author origami
 * @date 2022/1/21 21:36
 */
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    private TokenService tokenService;
    @Resource
    private SysUserService sysUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        extraOperation(authentication);

        PrintWriter writer = response.getWriter();
        String token = tokenService.generateToken(authentication);

        writer.write(JsonUtil.toJson(R.ok(ImmutableMap.of(SecurityConstants.AUTHENTICATION_HEADER_NAME, token))));
    }

    private void extraOperation(Authentication authentication) {
        MemberDetails principal = (MemberDetails)authentication.getPrincipal();
        Long id = principal.getId();
        SysUserDO userDO = sysUserService.getById(id);
        SysUserDO sysUserDO = new SysUserDO();
        sysUserDO.setLastLoginTime(LocalDateTime.now()).setVersion(userDO.getVersion()).setId(id);
        sysUserService.updateById(sysUserDO);
    }
}
