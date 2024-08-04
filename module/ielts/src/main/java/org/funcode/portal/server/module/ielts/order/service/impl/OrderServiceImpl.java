/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.order.service.impl;

import lombok.RequiredArgsConstructor;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.core.module.ielts.domain.Order;
import org.funcode.portal.server.module.ielts.order.repository.IOrderRepository;
import org.funcode.portal.server.module.ielts.order.service.IOrderService;
import org.springframework.stereotype.Service;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements IOrderService {

    private final IOrderRepository orderRepository;

    /**
     * @return base dao
     */
    @Override
    public IOrderRepository getBaseRepository() {
        return this.orderRepository;
    }
}
