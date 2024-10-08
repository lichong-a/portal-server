/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.funcode.portal.server.common.core.base.exception.BusinessException;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.domain.base.PageRequestVo;
import org.funcode.portal.server.common.domain.ielts.Order;
import org.funcode.portal.server.common.domain.ielts.Order_;
import org.funcode.portal.server.common.domain.security.User;
import org.funcode.portal.server.module.ielts.order.domain.vo.OrderQueryVo;
import org.funcode.portal.server.module.ielts.order.repository.IOrderRepository;
import org.funcode.portal.server.module.ielts.order.service.IOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
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
                                orderQueryVo.getId() != null ? criteriaBuilder.equal(root.get(Order_.id), orderQueryVo.getId()) : criteriaBuilder.conjunction(),
                                orderQueryVo.getTradeType() != null ? criteriaBuilder.equal(root.get(Order_.tradeType), orderQueryVo.getTradeType()) : criteriaBuilder.conjunction(),
                                orderQueryVo.getPriceMin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(Order_.price), orderQueryVo.getPriceMin()) : criteriaBuilder.conjunction(),
                                orderQueryVo.getPriceMax() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(Order_.price), orderQueryVo.getPriceMax()) : criteriaBuilder.conjunction(),
                                orderQueryVo.getPaymentTimeBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(Order_.paymentTime), orderQueryVo.getPaymentTimeBegin()) : criteriaBuilder.conjunction(),
                                orderQueryVo.getPaymentTimeEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(Order_.paymentTime), orderQueryVo.getPaymentTimeEnd()) : criteriaBuilder.conjunction(),
                                orderQueryVo.getCreatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(Order_.createdAt), orderQueryVo.getCreatedAtBegin()) : criteriaBuilder.conjunction(),
                                orderQueryVo.getCreatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(Order_.createdAt), orderQueryVo.getCreatedAtEnd()) : criteriaBuilder.conjunction(),
                                orderQueryVo.getUpdatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(Order_.updatedAt), orderQueryVo.getUpdatedAtBegin()) : criteriaBuilder.conjunction(),
                                orderQueryVo.getUpdatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(Order_.updatedAt), orderQueryVo.getUpdatedAtEnd()) : criteriaBuilder.conjunction()
                        )
                ).getRestriction(),
                orderQueryVo.getPageRequest()
        );
    }

    @Override
    public Page<Order> pageListCurrentUser(PageRequestVo pageRequestVo) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUser == null) {
            throw new BusinessException("当前用户未登录");
        }
        return getBaseRepository().findAll(
                (Specification<Order>) (root, query, criteriaBuilder) -> query.where(criteriaBuilder.and(
                                criteriaBuilder.equal(root.get(Order_.user), currentUser)
                        )
                ).getRestriction(),
                pageRequestVo.getPageRequest()
        );
    }
}
