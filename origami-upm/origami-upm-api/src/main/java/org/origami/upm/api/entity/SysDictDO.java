package org.origami.upm.api.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;
import org.origami.common.mybatis.entity.BaseEntity;


/**
 * 系统字典表
 *
 * @author origami
 * @date 2022/1/11 22:42
 */
@Data
@TableName("sys_dict")
@Accessors(chain = true)
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
public class SysDictDO extends BaseEntity {

    private static final long serialVersionUID = 5442935754916186338L;

    @ApiModelProperty("字典类型id")
    private Long dictTypeId;

    private String dictKey;

    private String dictValue;

    private String description;

    @ApiModelProperty(value = "是否允许编辑修改,0表示不允许修改,1表示允许修改")
    @TableField("is_fixed")
    private Integer fixed;
}
