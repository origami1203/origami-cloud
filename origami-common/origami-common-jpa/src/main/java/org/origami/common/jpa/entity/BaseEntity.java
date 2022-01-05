package org.origami.common.jpa.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
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
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author yuanmenglong
 * @version 1.0.0
 * @date 2022-01-05 10:10
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
    @GeneratedValue(generator = "snowflake")
    @GenericGenerator(name = "snowflake", strategy = "org.origami.common.jpa.utils.SnowflakeIdGenerator")
    @ApiModelProperty(value = "主键")
    @Column(columnDefinition = "bigint unsigned comment '主键'")
    protected Long id;
    /**
     * 创建时间
     */
    @CreatedDate
    @ApiModelProperty(value = "创建日期")
    @Column(columnDefinition = "datetime not null comment '创建日期'")
    protected LocalDateTime createDate;
    /**
     * 最后更新时间
     */
    @LastModifiedDate
    @ApiModelProperty(value = "更新日期")
    @Column(columnDefinition = "datetime not null comment '更新日期'")
    protected LocalDateTime updateDate;
    /**
     * 创建人
     */
    @CreatedBy
    @ApiModelProperty(value = "创建人")
    @Column(columnDefinition = "varchar(50) comment '创建人'")
    protected String createBy;
    /**
     * 最后更新人
     */
    @LastModifiedBy
    @ApiModelProperty(value = "最后更新人")
    @Column(columnDefinition = "varchar(50) comment '最后更新人'")
    protected String updateBy;
    /**
     * 逻辑删除
     */
    @ApiModelProperty(value = "逻辑删除")
    @Column(name = "is_deleted", columnDefinition = "tinyint default 0 comment '逻辑删除'")
    protected Boolean deleted;

    /**
     * 乐观锁
     */
    @Version
    @ApiModelProperty(value = "版本号")
    @Column(columnDefinition = "bigint default 0 not null comment '版本号'")
    protected Long version;
}
