package org.origami.common.mybatis.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
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
    protected LocalDateTime createDate;
    /**
     * 最后更新时间
     */
    @ApiModelProperty(value = "更新日期")
    protected LocalDateTime updateDate;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    protected String createBy;
    /**
     * 最后更新人
     */
    @ApiModelProperty(value = "最后更新人")
    protected String updateBy;
    /**
     * 逻辑删除
     */
    @TableLogic
    @ApiModelProperty(value = "逻辑删除")
    @TableField(value = "is_deleted")
    protected Boolean deleted;
    
    /**
     * 乐观锁
     */
    @Version
    @ApiModelProperty(value = "版本号")
    protected Long version;
}
