/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.carousel.service.impl;

import lombok.RequiredArgsConstructor;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.domain.ielts.Carousel;
import org.funcode.portal.server.module.ielts.carousel.repository.ICarouselRepository;
import org.funcode.portal.server.module.ielts.carousel.service.ICarouselService;
import org.springframework.stereotype.Service;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class CarouselServiceImpl extends BaseServiceImpl<Carousel, Long> implements ICarouselService {

    private final ICarouselRepository carouselRepository;

    /**
     * @return base dao
     */
    @Override
    public ICarouselRepository getBaseRepository() {
        return this.carouselRepository;
    }
}
