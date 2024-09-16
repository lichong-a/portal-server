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
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.proxy.HibernateProxy;

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
@Table(name = "tb_storage")
@Comment("存储管理表")
@Schema(description = "存储")
@DynamicUpdate
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Storage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @Comment("存储ID")
    @Schema(description = "存储ID")
    private Long id;

    @Column(length = 100, nullable = false)
    @Comment("标题")
    @Schema(description = "标题")
    private String title;

    @Column(length = 10)
    @Comment("文件类型（0:图片；1:音频；2:视频；3:markdown）")
    @Schema(description = "文件类型（0:图片；1:音频；2:视频；3:markdown）")
    private int fileType;

    @Column(length = 200, nullable = false)
    @Comment("桶名称")
    @Schema(description = "桶名称")
    private String bucketName;

    @Column(length = 300, nullable = false)
    @Comment("唯一标识")
    @Schema(description = "唯一标识")
    private String key;

    @Column(length = 200)
    @Comment("版本ID")
    @Schema(description = "版本ID")
    private String versionId;

    @OneToMany(mappedBy = "storage")
    @JsonIgnore
    @Comment("轮播")
    @Schema(description = "轮播")
    @ToString.Exclude
    private Set<Carousel> carousels;

    @OneToMany(mappedBy = "courseDescriptionStorage", cascade = CascadeType.ALL)
    @JsonIgnore
    @Comment("课程简介")
    @Schema(description = "课程简介")
    @ToString.Exclude
    private Set<Course> descriptionCourses;

    @OneToMany(mappedBy = "courseMediaStorage", cascade = CascadeType.ALL)
    @JsonIgnore
    @Comment("课程音视频")
    @Schema(description = "课程音视频")
    @ToString.Exclude
    private Set<Course> mediaCourses;

    @OneToMany(mappedBy = "courseCoverStorage", cascade = CascadeType.ALL)
    @JsonIgnore
    @Comment("课程封面")
    @Schema(description = "课程封面")
    @ToString.Exclude
    private Set<Course> coverCourses;

    @ManyToMany(mappedBy = "courseAttachmentStorages")
    @JsonIgnore
    @Comment("课程资料")
    @Schema(description = "课程资料")
    @ToString.Exclude
    private Set<Course> attachmentCourses;

    @OneToMany(mappedBy = "courseColumnDescriptionStorage", cascade = CascadeType.ALL)
    @JsonIgnore
    @Comment("课程专栏简介")
    @Schema(description = "课程专栏简介")
    @ToString.Exclude
    private Set<CourseColumn> descriptionCourseColumns;

    @OneToMany(mappedBy = "courseColumnCoverStorage", cascade = CascadeType.ALL)
    @JsonIgnore
    @Comment("课程专栏封面")
    @Schema(description = "课程专栏封面")
    @ToString.Exclude
    private Set<CourseColumn> coverCourseColumns;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Storage storage = (Storage) o;
        return getId() != null && Objects.equals(getId(), storage.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
