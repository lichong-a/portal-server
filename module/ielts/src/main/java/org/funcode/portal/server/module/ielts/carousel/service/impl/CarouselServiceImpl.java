/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.carousel.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.domain.ielts.Carousel;
import org.funcode.portal.server.common.domain.ielts.Carousel_;
import org.funcode.portal.server.common.domain.ielts.Storage;
import org.funcode.portal.server.module.ielts.carousel.domain.vo.CarouselAddOrEditVo;
import org.funcode.portal.server.module.ielts.carousel.domain.vo.CarouselQueryVo;
import org.funcode.portal.server.module.ielts.carousel.repository.ICarouselRepository;
import org.funcode.portal.server.module.ielts.carousel.service.ICarouselService;
import org.funcode.portal.server.module.ielts.storage.repository.IStorageRepository;
import org.springframework.beans.BeanUtils;
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
public class CarouselServiceImpl extends BaseServiceImpl<Carousel, Long> implements ICarouselService {

    private final ICarouselRepository carouselRepository;
    private final IStorageRepository storageRepository;

    @Override
    public ICarouselRepository getBaseRepository() {
        return this.carouselRepository;
    }

    @Override
    @Transactional
    public Page<Carousel> findPage(CarouselQueryVo carouselQueryVo) {
        return getBaseRepository().findAll(
                (Specification<Carousel>) (root, query, criteriaBuilder) -> query.where(criteriaBuilder.and(
                                StringUtils.isNotBlank(carouselQueryVo.getTitle()) ? criteriaBuilder.like(root.get(Carousel_.title), "%" + carouselQueryVo.getTitle() + "%") : criteriaBuilder.conjunction(),
                                carouselQueryVo.getId() != null ? criteriaBuilder.equal(root.get(Carousel_.id), carouselQueryVo.getId()) : criteriaBuilder.conjunction(),
                                carouselQueryVo.getCreatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(Carousel_.createdAt), carouselQueryVo.getCreatedAtBegin()) : criteriaBuilder.conjunction(),
                                carouselQueryVo.getCreatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(Carousel_.createdAt), carouselQueryVo.getCreatedAtEnd()) : criteriaBuilder.conjunction(),
                                carouselQueryVo.getUpdatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(Carousel_.updatedAt), carouselQueryVo.getUpdatedAtBegin()) : criteriaBuilder.conjunction(),
                                carouselQueryVo.getUpdatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(Carousel_.updatedAt), carouselQueryVo.getUpdatedAtEnd()) : criteriaBuilder.conjunction()
                        )
                ).getRestriction(),
                carouselQueryVo.getPageRequest()
        );
    }

    @Override
    @Transactional
    public Carousel addOrEdit(CarouselAddOrEditVo carouselAddOrEditVo) {
        Carousel carousel = transAddOrEditVoToCarousel(carouselAddOrEditVo);
        if (carousel == null) return null;
        return getBaseRepository().save(carousel);
    }

    private Carousel transAddOrEditVoToCarousel(CarouselAddOrEditVo carouselAddOrEditVo) {
        Long storageId = carouselAddOrEditVo.getStorageId();
        if (storageId == null) {
            return null;
        }
        Storage storage = storageRepository.getReferenceById(storageId);
        Carousel carousel = new Carousel();
        BeanUtils.copyProperties(carouselAddOrEditVo, carousel);
        carousel.setStorage(storage);
        return carousel;
    }
}
