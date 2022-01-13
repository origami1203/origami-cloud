package org.origami.upm.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.origami.common.mybatis.entity.BaseEntity;

import java.time.LocalDate;

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
@ApiModel(description = "用户实体类")
public class SysUser extends BaseEntity {
    
    @ApiModelProperty(value = "用户名，登陆用户名")
    private String username;
    
    @ApiModelProperty(value = "昵称，显示的用户名")
    private String nickname;
    
    @JsonIgnore
    @ApiModelProperty(value = "密码")
    private String password;
    
    @ApiModelProperty(value = "联系电话")
    private String phone;
    
    @ApiModelProperty(value = "性别")
    private Integer gender;
    
    @ApiModelProperty(value = "生日")
    private LocalDate birthday;
    
    @ApiModelProperty(value = "是否启用")
    @TableField(value = "is_enabled")
    private Boolean enabled;
}
