/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.domain.ielts;


import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "tb_course",
        indexes = @Index(name = "index_tb_course_status", columnList = Course_.STATUS))
@Comment("课程管理表")
@Schema(description = "课程")
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Course extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @Comment("课程ID")
    @Schema(description = "课程ID")
    private Long id;

    @Column(length = 100, nullable = false)
    @Comment("课程标题")
    @Schema(description = "课程标题")
    private String title;

    @Column(nullable = false)
    @Comment("课程状态（0：已下架;1：已上架;2：下架并静止播放）")
    @Schema(description = "课程状态（0：已下架;1：已上架;2：下架并静止播放）")
    private Integer status;

    @Column
    @Comment("课程价格")
    @Schema(description = "课程价格")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_description_storage_id", referencedColumnName = "id")
    @Comment("课程简介文件")
    @Schema(description = "课程简介文件")
    private Storage courseDescriptionStorage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_media_storage_id", referencedColumnName = "id")
    @Comment("课程音视频文件")
    @Schema(description = "课程音视频文件")
    private Storage courseMediaStorage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_cover_storage_id", referencedColumnName = "id")
    @Comment("课程封面文件")
    @Schema(description = "课程封面文件")
    private Storage courseCoverStorage;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_course_course_attachment_storages",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "storage_id"))
    @ToString.Exclude
    private Set<Storage> courseAttachmentStorages;

    @ManyToMany(mappedBy = "courses")
    @ToString.Exclude
    @JsonBackReference
    private Set<CourseColumn> courseColumns;

    @ManyToMany
    @JoinTable(
            name = "tb_order_tb_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    @ToString.Exclude
    @JsonBackReference
    private Set<Order> orders;

    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    @ToString.Exclude
    private Set<RedeemCode> redeemCodes;

    @ManyToMany(mappedBy = "courses")
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
        Course course = (Course) o;
        return getId() != null && Objects.equals(getId(), course.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
