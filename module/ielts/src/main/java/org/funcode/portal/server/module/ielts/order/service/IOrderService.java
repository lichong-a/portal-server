/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.order.service;

import org.funcode.portal.server.common.core.base.service.IBaseService;
import org.funcode.portal.server.common.domain.ielts.Order;
import org.funcode.portal.server.module.ielts.order.domain.vo.OrderQueryVo;
import org.springframework.data.domain.Page;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
public interface IOrderService extends IBaseService<Order, Long> {

    /**
     * 分页查询订单信息
     *
     * @param orderQueryVo 查询参数
     * @return 分页结果
     */
    Page<Order> pageList(OrderQueryVo orderQueryVo);

}
