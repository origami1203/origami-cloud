package org.origami.auth.api.dto;

import lombok.Data;

/**
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-02-16 13:40
 */
@Data
public class LoginParam {
    private String username;
    private String password;
    private String refreshToken;
}
