package org.origami.common.log.utils;

import lombok.experimental.UtilityClass;
import org.origami.common.core.utils.IpUtils;
import org.origami.upm.api.entity.SysLog;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统操作日志工具类
 *
 * @author origami
 * @date 2021/12/30 22:30
 */
@UtilityClass
public class SysLogUtil {

    public SysLog getLogFromRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        SysLog sysLog = new SysLog();

        String ip = IpUtils.getIpAddress(request);
        String uri = request.getRequestURI();
        String url = request.getRequestURL().toString();
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);

        return sysLog.setIp(ip).setUri(uri).setUrl(url).setUserAgent(userAgent);
    }
}
