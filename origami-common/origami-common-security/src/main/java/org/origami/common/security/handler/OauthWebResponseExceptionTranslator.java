package org.origami.common.security.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-02-18 13:07
 */
public class OauthWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    @Override
    public ResponseEntity translate(Exception e) throws Exception {
        return null;
    }
}
