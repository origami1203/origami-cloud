package org.origami.common.core.data.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 实体类基类
 *
 * @author origami
 * @date 2022/4/11 23:02
 */
@Data
@Accessors(chain = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Id
    @GeneratedValue(generator = "snowflake")
    @GenericGenerator(name = "snowflake", strategy = "org.origami.common.jpa.utils.SnowflakeIdGenerator")
    @ApiModelProperty(value = "主键")
    @Column(columnDefinition = "bigint unsigned comment '主键'")
    protected Long id;
    
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建日期")
    @TableField(fill = FieldFill.INSERT)
    @CreatedDate
    @Column(columnDefinition = "datetime not null comment '创建日期'")
    protected LocalDateTime createDate;
    
    /**
     * 最后更新时间
     */
    @LastModifiedDate
    @ApiModelProperty(value = "更新日期")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Column(columnDefinition = "datetime not null comment '更新日期'")
    protected LocalDateTime updateDate;
    
    /**
     * 创建人
     */
    @CreatedBy
    @ApiModelProperty(value = "创建人")
    @TableField(fill = FieldFill.INSERT)
    @Column(columnDefinition = "varchar(50) comment '创建人'")
    protected String createBy;
    
    /**
     * 最后更新人
     */
    @LastModifiedBy
    @ApiModelProperty(value = "最后更新人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Column(columnDefinition = "varchar(50) comment '最后更新人'")
    protected String updateBy;
    
    /**
     * 逻辑删除
     */
    @TableLogic(value = "0", delval = "1")
    @ApiModelProperty(value = "逻辑删除")
    @TableField(value = "is_deleted", fill = FieldFill.INSERT)
    @Column(name = "is_deleted", columnDefinition = "tinyint default 0 comment '逻辑删除'")
    protected Boolean deleted;
    
    /**
     * 乐观锁
     */
    @Version
    @javax.persistence.Version
    @ApiModelProperty(value = "版本号")
    @TableField(fill = FieldFill.INSERT)
    @Column(columnDefinition = "bigint unsigned default 0 not null comment '版本号'")
    protected Long version;
}
