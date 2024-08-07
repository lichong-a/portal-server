/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.domain.ielts;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
@EqualsAndHashCode(callSuper = true, exclude = {"orders", "redeemCodes", "users"})
@ToString(callSuper = true, exclude = {"orders", "redeemCodes", "users"})
@Table(name = "tb_course_column")
@Comment("课程专栏管理表")
@DynamicUpdate
public class CourseColumn extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @Comment("课程专栏ID")
    private Long id;

    @Column(length = 100, nullable = false)
    @Comment("课程专栏标题")
    private String title;

    @Column(nullable = false)
    @Comment("专栏状态（0：已下架;1：已上架;2：下架并静止播放）")
    private int status;

    @Column(nullable = false)
    @Comment("课程专栏价格")
    private BigDecimal price;

    @OneToOne
    @JoinColumn(name = "course_column_description_storage_id", referencedColumnName = "id")
    @Comment("课程专栏简介文件")
    private Storage courseColumnDescriptionStorage;

    @OneToOne
    @JoinColumn(name = "course_column_cover_storage_id", referencedColumnName = "id")
    @Comment("课程专栏封面文件")
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
    private Set<Order> orders;

    @ManyToMany
    @JoinTable(
            name = "tb_redeem_code_course_column",
            joinColumns = @JoinColumn(name = "course_column_id"),
            inverseJoinColumns = @JoinColumn(name = "redeem_code_id"))
    @JsonIgnore
    private Set<RedeemCode> redeemCodes;

    @ManyToMany
    @JoinTable(
            name = "tb_user_course_column",
            joinColumns = @JoinColumn(name = "course_column_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private Set<User> users;

    public static final class ColumnName {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String STATUS = "status";
        public static final String PRICE = "price";
        public static final String COURSE_COLUMN_DESCRIPTION_STORAGE_ID = "course_column_description_storage_id";
        public static final String COURSE_COLUMN_COVER_STORAGE_ID = "course_column_cover_storage_id";
    }

}
