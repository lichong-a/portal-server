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

import java.math.BigDecimal;
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
@Table(name = "tb_order")
@Comment("订单管理表")
@DynamicUpdate
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @Comment("订单ID")
    private Long id;

    @Column(nullable = false)
    @Comment("交易方式（0：兑换码;1：微信支付）")
    private int tradeType;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @Comment("付款时间")
    private LocalDateTime paymentTime;

    @Column
    @Comment("金额")
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "order_user_id", referencedColumnName = "id")
    @Comment("人员")
    private User user;

    @ManyToMany(mappedBy = "orders")
    private Set<Course> courses;

    @ManyToMany(mappedBy = "orders")
    private Set<CourseColumn> courseColumns;

}
