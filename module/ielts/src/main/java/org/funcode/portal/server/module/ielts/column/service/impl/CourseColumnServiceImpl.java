/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.column.service.impl;

import lombok.RequiredArgsConstructor;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.core.module.ielts.domain.CourseColumn;
import org.funcode.portal.server.module.ielts.column.repository.ICourseColumnRepository;
import org.funcode.portal.server.module.ielts.column.service.ICourseColumnService;
import org.springframework.stereotype.Service;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class CourseColumnServiceImpl extends BaseServiceImpl<CourseColumn, Long> implements ICourseColumnService {

    private final ICourseColumnRepository courseColumnRepository;

    /**
     * @return base dao
     */
    @Override
    public ICourseColumnRepository getBaseRepository() {
        return this.courseColumnRepository;
    }
}
