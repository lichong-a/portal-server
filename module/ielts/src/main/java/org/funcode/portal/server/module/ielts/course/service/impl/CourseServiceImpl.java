/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.course.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.base.exception.BusinessException;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.domain.base.PageRequestVo;
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
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

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
                                StringUtils.isNotBlank(courseQueryVo.getTitle()) ? criteriaBuilder.like(root.get(Course_.title), "%" + courseQueryVo.getTitle() + "%") : criteriaBuilder.conjunction(),
                                courseQueryVo.getId() != null ? criteriaBuilder.equal(root.get(Course_.id), courseQueryVo.getId()) : criteriaBuilder.conjunction(),
                                courseQueryVo.getStatus() != null ? criteriaBuilder.equal(root.get(Course_.status), courseQueryVo.getStatus()) : criteriaBuilder.conjunction(),
                                courseQueryVo.getCreatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(Course_.createdAt), courseQueryVo.getCreatedAtBegin()) : criteriaBuilder.conjunction(),
                                courseQueryVo.getCreatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(Course_.createdAt), courseQueryVo.getCreatedAtEnd()) : criteriaBuilder.conjunction(),
                                courseQueryVo.getUpdatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(Course_.updatedAt), courseQueryVo.getUpdatedAtBegin()) : criteriaBuilder.conjunction(),
                                courseQueryVo.getUpdatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(Course_.updatedAt), courseQueryVo.getUpdatedAtEnd()) : criteriaBuilder.conjunction()
                        )
                ).getRestriction(),
                courseQueryVo.getPageRequest()
        );
    }

    @Override
    @Transactional
    public Page<Course> findPage(PageRequestVo pageRequestVo) {
        return getBaseRepository().findAll(
                (Specification<Course>) (root, query, criteriaBuilder) -> query.where(
                        criteriaBuilder.equal(root.get(Course_.status), 1)
                ).getRestriction(),
                pageRequestVo.getPageRequest()
        );
    }

    private Course transAddOrEditVoToCourse(CourseAddOrEditVo courseAddOrEditVo) {
        Long mediaStorageId = courseAddOrEditVo.getCourseMediaStorageId();
        if (mediaStorageId == null) {
            throw new BusinessException("media storage id is null");
        }
        Storage mediaStorage = storageRepository.getReferenceById(mediaStorageId);
        Storage coverStorage = courseAddOrEditVo.getCourseCoverStorageId() != null ?
                storageRepository.getReferenceById(courseAddOrEditVo.getCourseCoverStorageId())
                : null;
        Storage descriptionStorage = courseAddOrEditVo.getCourseDescriptionStorageId() != null ?
                storageRepository.getReferenceById(courseAddOrEditVo.getCourseDescriptionStorageId())
                : null;
        List<Long> courseAttachmentStorageIds = courseAddOrEditVo.getCourseAttachmentStorageIds();
        List<Storage> courseAttachmentStorages = CollectionUtils.isEmpty(courseAttachmentStorageIds) ? Collections.emptyList() : storageRepository.findAllById(courseAttachmentStorageIds);
        return Course.builder()
                .id(courseAddOrEditVo.getId())
                .title(courseAddOrEditVo.getTitle())
                .price(courseAddOrEditVo.getPrice())
                .status(courseAddOrEditVo.getStatus())
                .courseMediaStorage(mediaStorage)
                .courseCoverStorage(coverStorage)
                .courseDescriptionStorage(descriptionStorage)
                .courseAttachmentStorages(CollectionUtils.isEmpty(courseAttachmentStorages) ? Collections.emptySet() : new HashSet<>(courseAttachmentStorages))
                .build();
    }
}
