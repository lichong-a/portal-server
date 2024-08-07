/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.domain.ielts;


import jakarta.persistence.*;
import lombok.*;
import org.funcode.portal.server.common.domain.base.BaseEntity;
import org.funcode.portal.server.common.domain.security.User;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
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
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "tb_carousel")
@Comment("兑换码管理表")
@DynamicUpdate
public class RedeemCode extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @Comment("兑换码ID")
    private Long id;

    @Column(nullable = false)
    @Comment("状态（0：待消费;1：已消费；2:已弃用）")
    private int status;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @Comment("过期时间")
    private LocalDateTime expireTime;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @Comment("兑换时间")
    private LocalDateTime redeemTime;

    @ManyToMany(mappedBy = "redeemCodes")
    private Set<Course> courses;

    @ManyToMany(mappedBy = "redeemCodes")
    private Set<CourseColumn> courseColumns;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "redeem_code_user_id", referencedColumnName = "id")
    @Comment("人员")
    private User user;

    public static final class ColumnName {
        public static final String ID = "id";
        public static final String STATUS = "status";
        public static final String EXPIRE_TIME = "expire_time";
        public static final String REDEEM_TIME = "redeem_time";
        public static final String REDEEM_CODE_USER_ID = "redeem_code_user_id";
    }

}
