/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.course.service;

import jakarta.validation.Valid;
import org.funcode.portal.server.common.core.base.service.IBaseService;
import org.funcode.portal.server.common.domain.base.PageRequestVo;
import org.funcode.portal.server.common.domain.ielts.Course;
import org.funcode.portal.server.module.ielts.course.domain.vo.CourseAddOrEditVo;
import org.funcode.portal.server.module.ielts.course.domain.vo.CourseQueryVo;
import org.springframework.data.domain.Page;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
public interface ICourseService extends IBaseService<Course, Long> {

    /**
     * 添加或编辑课程
     *
     * @param courseAddOrEditVo 课程添加或编辑VO
     * @return 课程
     */
    Course addOrEdit(CourseAddOrEditVo courseAddOrEditVo);

    /**
     * 根据条件分页查询课程列表
     *
     * @param courseQueryVo 课程查询条件VO
     * @return 课程分页列表
     */
    Page<Course> findPage(@Valid CourseQueryVo courseQueryVo);

    /**
     * 首页分页查询已上架课程列表
     *
     * @param pageRequestVo 分页请求
     * @return 课程分页列表
     */
    Page<Course> findPage(@Valid PageRequestVo pageRequestVo);
}
