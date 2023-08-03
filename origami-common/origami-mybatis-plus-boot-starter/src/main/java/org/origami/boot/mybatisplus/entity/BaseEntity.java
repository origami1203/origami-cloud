package org.origami.boot.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

import lombok.experimental.FieldNameConstants;

/**
 * @author origami
 * @date 2022/1/5 18:28
 */
@Data
@Accessors(chain = true)
@FieldNameConstants
public abstract class BaseEntity implements Serializable {
    
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    protected Long id;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createDate;
    /**
     * 最后更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updateDate;
    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    protected String createBy;
    
    /**
     * 创建人id
     */
    @TableField(fill = FieldFill.INSERT)
    protected Long createById;
    
    /**
     * 更新人id
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected Long updateById;
    
    /**
     * 最后更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected String updateBy;
    /**
     * 逻辑删除
     */
    @TableLogic(value = "0", delval = "1")
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    protected Boolean deleted;
    
    /**
     * 乐观锁
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    protected Long version;
}
