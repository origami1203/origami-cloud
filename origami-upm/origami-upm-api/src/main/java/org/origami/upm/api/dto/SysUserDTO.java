package org.origami.upm.api.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 包含角色信息的user对象
 *
 * @author origami
 * @date 2022/1/11 23:46
 */
@Data
@Accessors(chain = true)
public class SysUserDTO {
    private Long id;
    private String username;
    private String password;
    private Boolean enabled;
    
    private List<String> roles;
}
