package org.origami.common.log.utils;

import lombok.experimental.UtilityClass;
import org.origami.common.core.utils.IpUtils;
import org.origami.common.log.base.SysLogInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 系统操作日志工具类
 *
 * @author origami
 * @date 2021/12/30 22:30
 */
@UtilityClass
public class SysLogInfoUtil {
    
    public SysLogInfo getLogInfoFromServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request =
                ((ServletRequestAttributes) requestAttributes).getRequest();
        SysLogInfo sysLogInfo = new SysLogInfo();
        sysLogInfo.setOperatingTime(LocalDateTime.now());
        
        String ip = IpUtils.getIpAddress(request);
        String uri = request.getRequestURI();
        String url = request.getRequestURL().toString();
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
        
        return sysLogInfo.setIp(ip).setUri(uri).setUrl(url).setUserAgent(userAgent);
    }
}
