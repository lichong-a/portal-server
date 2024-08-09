/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.column.service;

import org.funcode.portal.server.common.core.base.service.IBaseService;
import org.funcode.portal.server.common.domain.ielts.CourseColumn;
import org.funcode.portal.server.module.ielts.column.domain.vo.CourseColumnAddOrEditVo;
import org.funcode.portal.server.module.ielts.column.domain.vo.CourseColumnQueryVo;
import org.springframework.data.domain.Page;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
public interface ICourseColumnService extends IBaseService<CourseColumn, Long> {

    /**
     * 添加或编辑专栏
     *
     * @param courseColumnAddOrEditVo 专栏添加或编辑VO
     * @return 课程
     */
    CourseColumn addOrEdit(CourseColumnAddOrEditVo courseColumnAddOrEditVo);

    /**
     * 根据条件分页查询专栏列表
     *
     * @param courseColumnQueryVo 专栏查询条件VO
     * @return 专栏分页列表
     */
    Page<CourseColumn> findPage(CourseColumnQueryVo courseColumnQueryVo);

}
