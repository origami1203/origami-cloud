package org.origami.common.mybatis.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author origami1203
 * @date 2021/12/29 18:18
 * @description 实体类继承该类，获取基础信息
 */
@Setter
@Getter
@Accessors(chain = true)
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 8464581702560616820L;
    
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;
    /**
     * 最后更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;
    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    /**
     * 最后更新人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    /**
     * 逻辑删除
     */
    @TableLogic
    private Boolean deleted;
    
    /**
     * 乐观锁
     */
    @Version
    private Long version;
}
