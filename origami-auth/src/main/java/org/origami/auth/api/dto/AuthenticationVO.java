package org.origami.auth.api.dto;

import lombok.Data;

/**
 * @author origami
 * @date 2022/2/16 20:58
 */
@Data
public class AuthenticationVO {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private Integer expires_in;
    private String scope;
}
