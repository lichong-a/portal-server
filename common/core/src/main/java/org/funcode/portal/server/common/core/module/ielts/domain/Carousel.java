/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.module.ielts.domain;


import jakarta.persistence.*;
import lombok.*;
import org.funcode.portal.server.common.core.base.entity.BaseEntity;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;

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
@EqualsAndHashCode(callSuper = true, exclude = {"storage"})
@ToString(callSuper = true, exclude = {"storage"})
@Table(name = "tb_carousel")
@Comment("轮播管理表")
@DynamicUpdate
public class Carousel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @Comment("轮播ID")
    private Long id;

    @Builder.Default
    @Column(nullable = false)
    @Comment("顺序")
    private long carouselOrder = 0;

    @Column(length = 100)
    @Comment("标题")
    private String title;

    @OneToOne
    @JoinColumn(name = "storage_id", referencedColumnName = "id")
    @Comment("图片文件")
    private Storage storage;

}
