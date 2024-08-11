/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.domain.ielts.Order;
import org.funcode.portal.server.common.domain.ielts.Order_;
import org.funcode.portal.server.module.ielts.order.domain.vo.OrderQueryVo;
import org.funcode.portal.server.module.ielts.order.repository.IOrderRepository;
import org.funcode.portal.server.module.ielts.order.service.IOrderService;
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
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements IOrderService {

    private final IOrderRepository orderRepository;

    @Override
    public IOrderRepository getBaseRepository() {
        return this.orderRepository;
    }

    @Override
    @Transactional
    public Page<Order> pageList(OrderQueryVo orderQueryVo) {
        return getBaseRepository().findAll(
                (Specification<Order>) (root, query, criteriaBuilder) -> query.where(criteriaBuilder.and(
                                orderQueryVo.getId() != null ? criteriaBuilder.equal(root.get(Order_.id), orderQueryVo.getId()) : null,
                                orderQueryVo.getTradeType() != null ? criteriaBuilder.equal(root.get(Order_.tradeType), orderQueryVo.getTradeType()) : null,
                                orderQueryVo.getPriceMin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(Order_.price), orderQueryVo.getPriceMin()) : null,
                                orderQueryVo.getPriceMax() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(Order_.price), orderQueryVo.getPriceMax()) : null,
                                orderQueryVo.getPaymentTimeBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(Order_.paymentTime), orderQueryVo.getPaymentTimeBegin()) : null,
                                orderQueryVo.getPaymentTimeEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(Order_.paymentTime), orderQueryVo.getPaymentTimeEnd()) : null,
                                orderQueryVo.getCreatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(Order_.createdAt), orderQueryVo.getCreatedAtBegin()) : null,
                                orderQueryVo.getCreatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(Order_.createdAt), orderQueryVo.getCreatedAtEnd()) : null,
                                orderQueryVo.getUpdatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(Order_.updatedAt), orderQueryVo.getUpdatedAtBegin()) : null,
                                orderQueryVo.getUpdatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(Order_.updatedAt), orderQueryVo.getUpdatedAtEnd()) : null
                        )
                ).getRestriction(),
                orderQueryVo.getPageRequest()
        );
    }
}
