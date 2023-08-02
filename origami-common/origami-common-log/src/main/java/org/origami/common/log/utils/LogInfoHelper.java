package org.origami.common.log.utils;

import lombok.experimental.UtilityClass;
import org.origami.common.core.utils.HttpRequestUtils;
import org.origami.common.log.base.OperationLogInfo;
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
public class LogInfoHelper {

    public OperationLogInfo getLogInfoFromServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request =
                ((ServletRequestAttributes) requestAttributes).getRequest();
        OperationLogInfo operationLogInfo = new OperationLogInfo();
        operationLogInfo.setOperationTime(LocalDateTime.now());

        String ip = HttpRequestUtils.getIpAddress(request);
        String uri = request.getRequestURI();
        String url = request.getRequestURL().toString();
        String userAgent = request.getHeader(HttpHeaders.USER_AGENT);

        return operationLogInfo.setIp(ip).setUri(uri).setUrl(url).setUserAgent(userAgent);
    }
}
