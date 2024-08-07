/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.domain.ielts;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
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
@EqualsAndHashCode(callSuper = true, exclude = {"carousel", "descriptionCourse", "mediaCourse", "coverCourse", "attachmentCourse"})
@ToString(callSuper = true, exclude = {"carousel", "descriptionCourse", "mediaCourse", "coverCourse", "attachmentCourse"})
@Table(name = "tb_storage")
@Comment("存储管理表")
@DynamicUpdate
public class Storage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ColumnName.ID, nullable = false, updatable = false)
    @Comment("存储ID")
    private Long id;

    @Column(name = ColumnName.TITLE, length = 100, nullable = false)
    @Comment("标题")
    private String title;

    @Column(name = ColumnName.FILE_TYPE, length = 10)
    @Comment("文件类型（0:图片；1:音频；2:视频；3:markdown）")
    private int fileType;

    @Column(name = ColumnName.BUCKET_NAME, length = 200, nullable = false)
    @Comment("桶名称")
    private String bucketName;

    @Column(name = ColumnName.KEY, length = 300, nullable = false)
    @Comment("唯一标识")
    private String key;

    @Column(name = ColumnName.VERSION_ID, length = 200, nullable = false)
    @Comment("版本ID")
    private String versionId;

    @OneToOne(mappedBy = "storage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("轮播")
    private Carousel carousel;

    @OneToOne(mappedBy = "courseDescriptionStorage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("课程简介")
    private Course descriptionCourse;

    @OneToOne(mappedBy = "courseMediaStorage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("课程音视频")
    private Course mediaCourse;

    @OneToOne(mappedBy = "courseCoverStorage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("课程封面")
    private Course coverCourse;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "attachment_course_id", referencedColumnName = "id")
    @JsonIgnore
    @Comment("课程资料")
    private Course attachmentCourse;

    @OneToOne(mappedBy = "courseColumnDescriptionStorage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("课程专栏简介")
    private CourseColumn descriptionCourseColumn;

    @OneToOne(mappedBy = "courseColumnCoverStorage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("课程专栏封面")
    private CourseColumn coverCourseColumn;

    public static final class ColumnName {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String FILE_TYPE = "file_type";
        public static final String BUCKET_NAME = "bucket_name";
        public static final String KEY = "key";
        public static final String VERSION_ID = "version_id";
    }

}
