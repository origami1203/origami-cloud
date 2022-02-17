package org.origami.auth.api.dto;

import lombok.Data;

/**
 * @author origami
 * @date 2022/2/16 20:58
 */
@Data
public class AuthenticationVO {
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private Integer expiresIn;
    private String scope;
}
