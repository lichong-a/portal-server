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

import java.math.BigDecimal;
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
@EqualsAndHashCode(callSuper = false, of = {"id", "title", "status", "price"})
@ToString(callSuper = true)
@Table(name = "tb_course")
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
    private int status;

    @Column(nullable = false)
    @Comment("课程价格")
    @Schema(description = "课程价格")
    private BigDecimal price;

    @OneToOne
    @JoinColumn(name = "course_description_storage_id", referencedColumnName = "id")
    @Comment("课程简介文件")
    @Schema(description = "课程简介文件")
    private Storage courseDescriptionStorage;

    @OneToOne
    @JoinColumn(name = "course_media_storage_id", referencedColumnName = "id")
    @Comment("课程音视频文件")
    @Schema(description = "课程音视频文件")
    private Storage courseMediaStorage;

    @OneToOne
    @JoinColumn(name = "course_cover_storage_id", referencedColumnName = "id")
    @Comment("课程封面文件")
    @Schema(description = "课程封面文件")
    private Storage courseCoverStorage;

    @OneToMany(mappedBy = "attachmentCourse")
    private Set<Storage> courseAttachmentStorages;

    @ManyToMany(mappedBy = "courses")
    @JsonIgnore
    private Set<CourseColumn> courseColumns;

    @ManyToMany
    @JoinTable(
            name = "tb_order_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    @JsonIgnore
    private Set<Order> orders;

    @ManyToMany
    @JoinTable(
            name = "tb_redeem_code_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "redeem_code_id"))
    @JsonIgnore
    private Set<RedeemCode> redeemCodes;

    @ManyToMany
    @JoinTable(
            name = "tb_user_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private Set<User> users;

}
