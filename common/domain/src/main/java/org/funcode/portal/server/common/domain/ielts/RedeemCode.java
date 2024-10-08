/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.domain.ielts;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.funcode.portal.server.common.domain.base.BaseEntity;
import org.funcode.portal.server.common.domain.security.User;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString(callSuper = true)
@Table(name = "tb_redeem_code",
        indexes = @Index(name = "index_tb_redeem_code_code", columnList = RedeemCode_.CODE))
@Comment("兑换码管理表")
@Schema(description = "兑换码")
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RedeemCode extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @Comment("兑换码ID")
    @Schema(description = "兑换码ID")
    private Long id;

    @Column(nullable = false)
    @Comment("状态（0：待消费;1：已消费；2:手动弃用）")
    @Schema(description = "状态（0：待消费;1：已消费；2:手动弃用）")
    private int status;

    @Column(nullable = false, unique = true, length = 200)
    @Comment("兑换码字符串")
    @Schema(description = "兑换码字符串")
    private String code;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Comment("过期时间")
    @Schema(description = "过期时间")
    private LocalDateTime expireTime;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Comment("兑换时间")
    @Schema(description = "兑换时间")
    private LocalDateTime redeemTime;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_redeem_code_course",
            joinColumns = @JoinColumn(name = "redeem_code_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_redeem_code_course_column",
            joinColumns = @JoinColumn(name = "redeem_code_id"),
            inverseJoinColumns = @JoinColumn(name = "course_column_id"))
    private Set<CourseColumn> courseColumns;

    @ManyToOne
    @JoinColumn(name = "redeem_code_user_id", referencedColumnName = "id")
    @Comment("兑换人")
    @Schema(description = "兑换人")
    private User user;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        RedeemCode that = (RedeemCode) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
