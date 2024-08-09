/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.carousel.service;

import org.funcode.portal.server.common.core.base.service.IBaseService;
import org.funcode.portal.server.common.domain.ielts.Carousel;
import org.funcode.portal.server.module.ielts.carousel.domain.vo.CarouselAddOrEditVo;
import org.funcode.portal.server.module.ielts.carousel.domain.vo.CarouselQueryVo;
import org.springframework.data.domain.Page;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
public interface ICarouselService extends IBaseService<Carousel, Long> {

    /**
     * 分页查询轮播信息
     *
     * @param carouselQueryVo 查询参数
     * @return 分页结果
     */
    Page<Carousel> findPage(CarouselQueryVo carouselQueryVo);

    /**
     * 新增或编辑轮播
     *
     * @param carouselAddOrEditVo 参数
     * @return 结果
     */
    Carousel addOrEdit(CarouselAddOrEditVo carouselAddOrEditVo);
}
