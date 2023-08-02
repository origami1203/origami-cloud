package org.origami.system.entity;

import java.util.Map;
import org.origami.boot.mybatisplus.entity.BaseEntity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

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

    private Long dictTypeId;

    private Long parentId;

    private String dictKey;

    private String dictValue;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> json;

    private String description;

    @TableField("is_fixed")
    private Integer fixed;
}
