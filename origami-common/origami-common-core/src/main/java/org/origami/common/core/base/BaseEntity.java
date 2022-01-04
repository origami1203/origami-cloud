package org.origami.common.core.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import nonapi.io.github.classgraph.json.Id;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * po实体类，提供基础的信息
 *
 * @author origami
 * @date 2022/1/4 19:29
 */
@Data
@Accessors(chain = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    /**
     * 主键
     */
    @Id
    @TableId(type = IdType.ASSIGN_ID)
    @GeneratedValue(generator = "snowflake")
    @GenericGenerator(name = "snowflake", strategy = "org.origami.common.jpa.utils.SnowflakeIdGenerator")
    @ApiModelProperty(value = "主键")
    @Column(columnDefinition = "bigint unsigned comment '主键'")
    protected Long id;
    /**
     * 创建时间
     */
    @CreatedDate
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建日期")
    @Column(columnDefinition = "datetime not null comment '创建日期'")
    protected LocalDateTime createDate;
    /**
     * 最后更新时间
     */
    @LastModifiedDate
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "更新日期")
    @Column(columnDefinition = "datetime not null comment '更新日期'")
    protected LocalDateTime updateDate;
    /**
     * 创建人
     */
    @CreatedBy
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人")
    @Column(columnDefinition = "varchar(50) comment '创建人'")
    protected String createBy;
    /**
     * 最后更新人
     */
    @LastModifiedBy
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "最后更新人")
    @Column(columnDefinition = "varchar(50) comment '最后更新人'")
    protected String updateBy;
    /**
     * 逻辑删除
     */
    @TableLogic
    @ApiModelProperty(value = "逻辑删除")
    @Column(name = "is_deleted", columnDefinition = "tinyint default 0 comment '逻辑删除'")
    protected Boolean deleted;
    
    /**
     * 乐观锁
     */
    @Version
    @com.baomidou.mybatisplus.annotation.Version
    @ApiModelProperty(value = "版本号")
    @Column(columnDefinition = "bigint default 0 not null comment '版本号'")
    protected Long version;
}
