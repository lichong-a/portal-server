/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.course.service.impl;

import lombok.RequiredArgsConstructor;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.domain.ielts.Course;
import org.funcode.portal.server.module.ielts.course.repository.ICourseRepository;
import org.funcode.portal.server.module.ielts.course.service.ICourseService;
import org.springframework.stereotype.Service;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class CourseServiceImpl extends BaseServiceImpl<Course, Long> implements ICourseService {

    private final ICourseRepository courseRepository;

    /**
     * @return base dao
     */
    @Override
    public ICourseRepository getBaseRepository() {
        return this.courseRepository;
    }
}
