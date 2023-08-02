package org.origami.system.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.origami.boot.mybatisplus.entity.BaseEntity;

/**
 * 用户
 *
 * @author origami
 * @date 2022/1/11 19:33
 */
@Data
@TableName(value = "sys_user")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysUserDO extends BaseEntity {

    private String username;

    private String nickname;

    private String password;

    private String phone;

    private String email;

    private Integer gender;

    private LocalDate birthday;

    @TableField(value = "is_enabled")
    private Boolean enabled;

    private LocalDateTime lastLoginTime;

    private String avatar;
}
