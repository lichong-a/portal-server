/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.domain.ielts;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.funcode.portal.server.common.domain.base.BaseEntity;
import org.funcode.portal.server.common.domain.security.User;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
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
@Table(name = "tb_course_column")
@Comment("课程专栏管理表")
@Schema(description = "课程专栏")
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CourseColumn extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @Comment("课程专栏ID")
    @Schema(description = "课程专栏ID")
    private Long id;

    @Column(length = 100, nullable = false)
    @Comment("课程专栏标题")
    @Schema(description = "课程专栏标题")
    private String title;

    @Column(nullable = false)
    @Comment("专栏状态（0：已下架;1：已上架;2：下架并静止播放）")
    @Schema(description = "专栏状态（0：已下架;1：已上架;2：下架并静止播放）")
    private int status;

    @Column(nullable = false)
    @Comment("课程专栏价格")
    @Schema(description = "课程专栏价格")
    private BigDecimal price;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "course_column_description_storage_id", referencedColumnName = "id")
    @Comment("课程专栏简介文件")
    @Schema(description = "课程专栏简介文件")
    private Storage courseColumnDescriptionStorage;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "course_column_cover_storage_id", referencedColumnName = "id")
    @Comment("课程专栏封面文件")
    @Schema(description = "课程专栏封面文件")
    private Storage courseColumnCoverStorage;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_course_course_column",
            joinColumns = @JoinColumn(name = "course_column_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private Set<Course> courses;

    @ManyToMany
    @JoinTable(
            name = "tb_order_course_column",
            joinColumns = @JoinColumn(name = "course_column_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    @JsonIgnore
    @ToString.Exclude
    private Set<Order> orders;

    @ManyToMany
    @JoinTable(
            name = "tb_redeem_code_course_column",
            joinColumns = @JoinColumn(name = "course_column_id"),
            inverseJoinColumns = @JoinColumn(name = "redeem_code_id"))
    @JsonIgnore
    @ToString.Exclude
    private Set<RedeemCode> redeemCodes;

    @ManyToMany
    @JoinTable(
            name = "tb_user_course_column",
            joinColumns = @JoinColumn(name = "course_column_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    @ToString.Exclude
    private Set<User> users;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CourseColumn that = (CourseColumn) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
