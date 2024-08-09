/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.column.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.domain.ielts.CourseColumn;
import org.funcode.portal.server.common.domain.ielts.CourseColumn_;
import org.funcode.portal.server.common.domain.ielts.Storage;
import org.funcode.portal.server.module.ielts.column.domain.vo.CourseColumnAddOrEditVo;
import org.funcode.portal.server.module.ielts.column.domain.vo.CourseColumnQueryVo;
import org.funcode.portal.server.module.ielts.column.repository.ICourseColumnRepository;
import org.funcode.portal.server.module.ielts.column.service.ICourseColumnService;
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
public class CourseColumnServiceImpl extends BaseServiceImpl<CourseColumn, Long> implements ICourseColumnService {

    private final ICourseColumnRepository courseColumnRepository;
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
                                StringUtils.isNotBlank(courseColumnQueryVo.getTitle()) ? criteriaBuilder.like(root.get(CourseColumn_.title), "%" + courseColumnQueryVo.getTitle() + "%") : null,
                                courseColumnQueryVo.getId() != null ? criteriaBuilder.equal(root.get(CourseColumn_.id), courseColumnQueryVo.getId()) : null,
                                courseColumnQueryVo.getStatus() != null ? criteriaBuilder.equal(root.get(CourseColumn_.status), courseColumnQueryVo.getStatus()) : null,
                                courseColumnQueryVo.getCreatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(CourseColumn_.createdAt), courseColumnQueryVo.getCreatedAtBegin()) : null,
                                courseColumnQueryVo.getCreatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(CourseColumn_.createdAt), courseColumnQueryVo.getCreatedAtEnd()) : null,
                                courseColumnQueryVo.getUpdatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(CourseColumn_.updatedAt), courseColumnQueryVo.getUpdatedAtBegin()) : null,
                                courseColumnQueryVo.getUpdatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(CourseColumn_.updatedAt), courseColumnQueryVo.getUpdatedAtEnd()) : null
                        )
                ).getRestriction(),
                courseColumnQueryVo.getPageRequest()
        );
    }

    private CourseColumn transAddOrEditVoToCourseColumn(CourseColumnAddOrEditVo courseColumnAddOrEditVo) {
        Storage coverStorage = storageRepository.getReferenceById(courseColumnAddOrEditVo.getCourseColumnCoverStorageId());
        Storage descriptionStorage = storageRepository.getReferenceById(courseColumnAddOrEditVo.getCourseColumnDescriptionStorageId());
        return CourseColumn.builder()
                .id(courseColumnAddOrEditVo.getId())
                .courseColumnCoverStorage(coverStorage)
                .courseColumnDescriptionStorage(descriptionStorage)
                .price(courseColumnAddOrEditVo.getPrice())
                .status(courseColumnAddOrEditVo.getStatus())
                .title(courseColumnAddOrEditVo.getTitle())
                .build();
    }
}
