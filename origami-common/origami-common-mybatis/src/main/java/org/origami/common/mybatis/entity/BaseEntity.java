package org.origami.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author origami
 * @date 2022/1/5 18:28
 */
@Data
@Accessors(chain = true)
public abstract class BaseEntity {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    protected Long id;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建日期")
    @TableField(fill = FieldFill.INSERT)
    protected LocalDateTime createDate;
    /**
     * 最后更新时间
     */
    @ApiModelProperty(value = "更新日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected LocalDateTime updateDate;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    @TableField(fill = FieldFill.INSERT)
    protected String createBy;
    /**
     * 最后更新人
     */
    @ApiModelProperty(value = "最后更新人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    protected String updateBy;
    /**
     * 逻辑删除
     */
    @JsonIgnore
    @TableLogic(value = "0", delval = "1")
    @ApiModelProperty(value = "逻辑删除")
    @TableField(value = "is_deleted")
    protected Boolean deleted;

    /**
     * 乐观锁
     */
    @Version
    @JsonIgnore
    @ApiModelProperty(value = "版本号")
    protected Long version;
}
