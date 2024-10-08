/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.column.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.base.exception.BusinessException;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.domain.base.PageRequestVo;
import org.funcode.portal.server.common.domain.ielts.Course;
import org.funcode.portal.server.common.domain.ielts.CourseColumn;
import org.funcode.portal.server.common.domain.ielts.CourseColumn_;
import org.funcode.portal.server.common.domain.ielts.Storage;
import org.funcode.portal.server.common.domain.security.User;
import org.funcode.portal.server.module.ielts.column.domain.vo.CourseColumnAddOrEditVo;
import org.funcode.portal.server.module.ielts.column.domain.vo.CourseColumnQueryVo;
import org.funcode.portal.server.module.ielts.column.repository.ICourseColumnRepository;
import org.funcode.portal.server.module.ielts.column.service.ICourseColumnService;
import org.funcode.portal.server.module.ielts.course.repository.ICourseRepository;
import org.funcode.portal.server.module.ielts.storage.repository.IStorageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class CourseColumnServiceImpl extends BaseServiceImpl<CourseColumn, Long> implements ICourseColumnService {

    private final ICourseColumnRepository courseColumnRepository;
    private final ICourseRepository courseRepository;
    private final IStorageRepository storageRepository;

    @Override
    public ICourseColumnRepository getBaseRepository() {
        return this.courseColumnRepository;
    }

    @Override
    @Transactional
    public CourseColumn addOrEdit(CourseColumnAddOrEditVo courseColumnAddOrEditVo) {
        CourseColumn courseColumn = transAddOrEditVoToCourseColumn(courseColumnAddOrEditVo);
        if (courseColumn == null) return null;
        return getBaseRepository().save(courseColumn);
    }

    @Override
    @Transactional
    public Page<CourseColumn> findPage(CourseColumnQueryVo courseColumnQueryVo) {
        return getBaseRepository().findAll(
                (Specification<CourseColumn>) (root, query, criteriaBuilder) -> query.where(criteriaBuilder.and(
                                StringUtils.isNotBlank(courseColumnQueryVo.getTitle()) ? criteriaBuilder.like(root.get(CourseColumn_.title), "%" + courseColumnQueryVo.getTitle() + "%") : criteriaBuilder.conjunction(),
                                courseColumnQueryVo.getId() != null ? criteriaBuilder.equal(root.get(CourseColumn_.id), courseColumnQueryVo.getId()) : criteriaBuilder.conjunction(),
                                courseColumnQueryVo.getStatus() != null ? criteriaBuilder.equal(root.get(CourseColumn_.status), courseColumnQueryVo.getStatus()) : criteriaBuilder.conjunction(),
                                courseColumnQueryVo.getCreatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(CourseColumn_.createdAt), courseColumnQueryVo.getCreatedAtBegin()) : criteriaBuilder.conjunction(),
                                courseColumnQueryVo.getCreatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(CourseColumn_.createdAt), courseColumnQueryVo.getCreatedAtEnd()) : criteriaBuilder.conjunction(),
                                courseColumnQueryVo.getUpdatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(CourseColumn_.updatedAt), courseColumnQueryVo.getUpdatedAtBegin()) : criteriaBuilder.conjunction(),
                                courseColumnQueryVo.getUpdatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(CourseColumn_.updatedAt), courseColumnQueryVo.getUpdatedAtEnd()) : criteriaBuilder.conjunction()
                        )
                ).getRestriction(),
                courseColumnQueryVo.getPageRequest()
        );
    }

    @Override
    public Page<CourseColumn> findPage(PageRequestVo pageRequestVo) {
        return getBaseRepository().findAll(
                (Specification<CourseColumn>) (root, query, criteriaBuilder) -> query.where(
                        criteriaBuilder.equal(root.get(CourseColumn_.status), 1)
                ).getRestriction(),
                pageRequestVo.getPageRequest()
        );
    }

    @Override
    public List<CourseColumn> listPurchased() {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUser == null) {
            throw new BusinessException("当前用户未登录");
        }
        return currentUser.getCourseColumns().stream().sorted((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt())).toList();
    }

    private CourseColumn transAddOrEditVoToCourseColumn(CourseColumnAddOrEditVo courseColumnAddOrEditVo) {
        Storage coverStorage = courseColumnAddOrEditVo.getCourseColumnCoverStorageId() != null ?
                storageRepository.getReferenceById(courseColumnAddOrEditVo.getCourseColumnCoverStorageId())
                : null;
        Storage descriptionStorage = courseColumnAddOrEditVo.getCourseColumnDescriptionStorageId() != null ?
                storageRepository.getReferenceById(courseColumnAddOrEditVo.getCourseColumnDescriptionStorageId())
                : null;
        List<Long> courseIds = courseColumnAddOrEditVo.getCourseIds();
        List<Course> courses = CollectionUtils.isEmpty(courseIds) ? Collections.emptyList() : courseRepository.findAllById(courseIds);
        return CourseColumn.builder()
                .id(courseColumnAddOrEditVo.getId())
                .courseColumnCoverStorage(coverStorage)
                .courseColumnDescriptionStorage(descriptionStorage)
                .price(courseColumnAddOrEditVo.getPrice())
                .status(courseColumnAddOrEditVo.getStatus())
                .title(courseColumnAddOrEditVo.getTitle())
                .courses(CollectionUtils.isEmpty(courses) ? Collections.emptySet() : new HashSet<>(courses))
                .build();
    }
}
