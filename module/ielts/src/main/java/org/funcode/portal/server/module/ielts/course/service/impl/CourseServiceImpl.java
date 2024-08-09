/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.course.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.domain.ielts.Course;
import org.funcode.portal.server.common.domain.ielts.Course_;
import org.funcode.portal.server.common.domain.ielts.Storage;
import org.funcode.portal.server.module.ielts.course.domain.vo.CourseAddOrEditVo;
import org.funcode.portal.server.module.ielts.course.domain.vo.CourseQueryVo;
import org.funcode.portal.server.module.ielts.course.repository.ICourseRepository;
import org.funcode.portal.server.module.ielts.course.service.ICourseService;
import org.funcode.portal.server.module.ielts.storage.repository.IStorageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends BaseServiceImpl<Course, Long> implements ICourseService {

    private final ICourseRepository courseRepository;
    private final IStorageRepository storageRepository;

    @Override
    public ICourseRepository getBaseRepository() {
        return this.courseRepository;
    }

    @Override
    @Transactional
    public Course addOrEdit(CourseAddOrEditVo courseAddOrEditVo) {
        Course course = transAddOrEditVoToCourse(courseAddOrEditVo);
        if (course == null) return null;
        return getBaseRepository().save(course);
    }

    @Override
    @Transactional
    public Page<Course> findPage(CourseQueryVo courseQueryVo) {
        return getBaseRepository().findAll(
                (Specification<Course>) (root, query, criteriaBuilder) -> query.where(criteriaBuilder.and(
                                StringUtils.isNotBlank(courseQueryVo.getTitle()) ? criteriaBuilder.like(root.get(Course_.title), "%" + courseQueryVo.getTitle() + "%") : null,
                                courseQueryVo.getId() != null ? criteriaBuilder.equal(root.get(Course_.id), courseQueryVo.getId()) : null,
                                courseQueryVo.getStatus() != null ? criteriaBuilder.equal(root.get(Course_.status), courseQueryVo.getStatus()) : null,
                                courseQueryVo.getCreatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(Course_.createdAt), courseQueryVo.getCreatedAtBegin()) : null,
                                courseQueryVo.getCreatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(Course_.createdAt), courseQueryVo.getCreatedAtEnd()) : null,
                                courseQueryVo.getUpdatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(Course_.updatedAt), courseQueryVo.getUpdatedAtBegin()) : null,
                                courseQueryVo.getUpdatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(Course_.updatedAt), courseQueryVo.getUpdatedAtEnd()) : null
                        )
                ).getRestriction(),
                courseQueryVo.getPageRequest()
        );
    }

    private Course transAddOrEditVoToCourse(CourseAddOrEditVo courseAddOrEditVo) {
        Long mediaStorageId = courseAddOrEditVo.getCourseMediaStorageId();
        if (mediaStorageId == null) {
            return null;
        }
        Storage mediaStorage = storageRepository.getReferenceById(mediaStorageId);
        Storage coverStorage = storageRepository.getReferenceById(courseAddOrEditVo.getCourseCoverStorageId());
        Storage descriptionStorage = storageRepository.getReferenceById(courseAddOrEditVo.getCourseDescriptionStorageId());
        return Course.builder()
                .id(courseAddOrEditVo.getId())
                .title(courseAddOrEditVo.getTitle())
                .price(courseAddOrEditVo.getPrice())
                .status(courseAddOrEditVo.getStatus())
                .courseMediaStorage(mediaStorage)
                .courseCoverStorage(coverStorage)
                .courseDescriptionStorage(descriptionStorage)
                .build();
    }
}
