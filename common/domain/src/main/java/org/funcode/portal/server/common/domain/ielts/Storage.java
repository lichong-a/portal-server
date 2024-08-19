/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.domain.ielts;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.funcode.portal.server.common.domain.base.BaseEntity;
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
@EqualsAndHashCode(callSuper = false, of = {"id", "title", "fileType", "bucketName", "key", "versionId"})
@ToString(callSuper = true)
@Table(name = "tb_storage")
@Comment("存储管理表")
@DynamicUpdate
public class Storage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    @Comment("存储ID")
    private Long id;

    @Column(length = 100, nullable = false)
    @Comment("标题")
    private String title;

    @Column(length = 10)
    @Comment("文件类型（0:图片；1:音频；2:视频；3:markdown）")
    private int fileType;

    @Column(length = 200, nullable = false)
    @Comment("桶名称")
    private String bucketName;

    @Column(length = 300, nullable = false)
    @Comment("唯一标识")
    private String key;

    @Column(length = 200, nullable = false)
    @Comment("版本ID")
    private String versionId;

    @OneToOne(mappedBy = "storage", cascade = CascadeType.ALL)
    @JsonIgnore
    @Comment("轮播")
    private Carousel carousel;

    @OneToOne(mappedBy = "courseDescriptionStorage", cascade = CascadeType.ALL)
    @JsonIgnore
    @Comment("课程简介")
    private Course descriptionCourse;

    @OneToOne(mappedBy = "courseMediaStorage", cascade = CascadeType.ALL)
    @JsonIgnore
    @Comment("课程音视频")
    private Course mediaCourse;

    @OneToOne(mappedBy = "courseCoverStorage", cascade = CascadeType.ALL)
    @JsonIgnore
    @Comment("课程封面")
    private Course coverCourse;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_course_id", referencedColumnName = "id")
    @JsonIgnore
    @Comment("课程资料")
    private Course attachmentCourse;

    @OneToOne(mappedBy = "courseColumnDescriptionStorage", cascade = CascadeType.ALL)
    @JsonIgnore
    @Comment("课程专栏简介")
    private CourseColumn descriptionCourseColumn;

    @OneToOne(mappedBy = "courseColumnCoverStorage", cascade = CascadeType.ALL)
    @JsonIgnore
    @Comment("课程专栏封面")
    private CourseColumn coverCourseColumn;

}
