/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.redeem.code.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.base.exception.BusinessException;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.core.security.repository.IUserRepository;
import org.funcode.portal.server.common.domain.ielts.*;
import org.funcode.portal.server.common.domain.security.User;
import org.funcode.portal.server.module.ielts.column.repository.ICourseColumnRepository;
import org.funcode.portal.server.module.ielts.course.repository.ICourseRepository;
import org.funcode.portal.server.module.ielts.order.repository.IOrderRepository;
import org.funcode.portal.server.module.ielts.redeem.code.domain.vo.RedeemCodeAddVo;
import org.funcode.portal.server.module.ielts.redeem.code.domain.vo.RedeemCodeQueryVo;
import org.funcode.portal.server.module.ielts.redeem.code.repository.IRedeemCodeRepository;
import org.funcode.portal.server.module.ielts.redeem.code.service.IRedeemCodeService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class RedeemCodeServiceImpl extends BaseServiceImpl<RedeemCode, Long> implements IRedeemCodeService {

    private final IRedeemCodeRepository redeemCodeRepository;
    private final ICourseRepository courseRepository;
    private final ICourseColumnRepository courseColumnRepository;
    private final IUserRepository userRepository;
    private final IOrderRepository orderRepository;

    @Override
    public IRedeemCodeRepository getBaseRepository() {
        return this.redeemCodeRepository;
    }

    @Override
    @Transactional
    public RedeemCode redeem(String code) {
        LocalDateTime now = LocalDateTime.now();
        RedeemCode redeemCode = getBaseRepository().findOne(
                (Specification<RedeemCode>) (root, query, criteriaBuilder) -> query.where(
                        criteriaBuilder.equal(root.get(RedeemCode_.code), code)
                ).getRestriction()).orElseThrow(() -> new BusinessException("兑换码不存在"));
        if (redeemCode.getStatus() == 1) throw new BusinessException("兑换码已使用");
        if (redeemCode.getStatus() == 2) throw new BusinessException("兑换码已作废");
        if (redeemCode.getStatus() != 0) throw new BusinessException("兑换码状态异常");
        if (redeemCode.getExpireTime().isBefore(now)) throw new BusinessException("兑换码已过期");
        // 变更为已消费状态
        redeemCode.setStatus(1);
        Set<Course> courses = redeemCode.getCourses();
        Set<CourseColumn> courseColumns = redeemCode.getCourseColumns();
        // 保存人员和课程/专栏关系
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        redeemCode.setUser(currentUser);
        currentUser.setCourses(courses);
        currentUser.setCourseColumns(courseColumns);
        userRepository.save(currentUser);
        // 保存订单
        Order order = Order.builder()
                .courses(courses)
                .courseColumns(courseColumns)
                .paymentTime(now)
                .price(BigDecimal.valueOf(0))
                .tradeType(0)
                .user(currentUser)
                .build();
        orderRepository.save(order);
        return getBaseRepository().save(redeemCode);
    }

    @Override
    @Transactional
    public RedeemCode addRedeemCode(RedeemCodeAddVo redeemCodeAddVo) {
        LocalDateTime expireTime = redeemCodeAddVo.getExpireTime() == null ? LocalDateTime.now().plusDays(1) : redeemCodeAddVo.getExpireTime();
        List<Long> courseColumnIds = redeemCodeAddVo.getCourseColumnIds();
        List<Long> courseIds = redeemCodeAddVo.getCourseIds();
        if (CollectionUtils.isEmpty(courseColumnIds) && CollectionUtils.isEmpty(courseIds)) {
            throw new BusinessException("专栏和课程至少需要选择一项，否则为无效兑换码");
        }
        if (expireTime.isBefore(LocalDateTime.now())) {
            throw new BusinessException("过期时间不能小于当前时间");
        }
        // 查找课程
        List<Course> courses = courseRepository.findAllById(courseIds);
        // 查找专栏
        List<CourseColumn> courseColumns = courseColumnRepository.findAllById(courseColumnIds);
        // 生成随机兑换码
        String code = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        RedeemCode redeemCode = RedeemCode.builder()
                .code(code)
                .expireTime(expireTime)
                .status(0)
                .courses(new HashSet<>(courses))
                .courseColumns(new HashSet<>(courseColumns))
                .build();
        return getBaseRepository().save(redeemCode);
    }

    @Override
    @Transactional
    public RedeemCode invalidRedeemCode(long redeemCodeId) {
        RedeemCode redeemCode = getBaseRepository().findById(redeemCodeId).orElseThrow(() -> new BusinessException("兑换码不存在"));
        redeemCode.setStatus(2);
        return getBaseRepository().save(redeemCode);
    }

    @Override
    @Transactional
    public Boolean deleteRedeemCode(String code) {
        return getBaseRepository().delete(
                (Specification<RedeemCode>) (root, query, criteriaBuilder) -> query.where(
                        criteriaBuilder.equal(root.get(RedeemCode_.code), code)
                ).getRestriction()) > 0;
    }

    @Override
    @Transactional
    public Page<RedeemCode> pageList(RedeemCodeQueryVo redeemCodeQueryVo) {
        return getBaseRepository().findAll(
                (Specification<RedeemCode>) (root, query, criteriaBuilder) -> query.where(criteriaBuilder.and(
                                StringUtils.isNotBlank(redeemCodeQueryVo.getCode()) ? criteriaBuilder.like(root.get(RedeemCode_.code), "%" + redeemCodeQueryVo.getCode() + "%") : null,
                                redeemCodeQueryVo.getId() != null ? criteriaBuilder.equal(root.get(RedeemCode_.id), redeemCodeQueryVo.getId()) : null,
                                redeemCodeQueryVo.getStatus() != null ? criteriaBuilder.equal(root.get(RedeemCode_.status), redeemCodeQueryVo.getStatus()) : null,
                                redeemCodeQueryVo.getExpireTimeBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(RedeemCode_.expireTime), redeemCodeQueryVo.getExpireTimeBegin()) : null,
                                redeemCodeQueryVo.getExpireTimeEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(RedeemCode_.expireTime), redeemCodeQueryVo.getExpireTimeEnd()) : null,
                                redeemCodeQueryVo.getRedeemTimeBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(RedeemCode_.redeemTime), redeemCodeQueryVo.getRedeemTimeBegin()) : null,
                                redeemCodeQueryVo.getRedeemTimeEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(RedeemCode_.redeemTime), redeemCodeQueryVo.getRedeemTimeEnd()) : null,
                                redeemCodeQueryVo.getCreatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(RedeemCode_.createdAt), redeemCodeQueryVo.getCreatedAtBegin()) : null,
                                redeemCodeQueryVo.getCreatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(RedeemCode_.createdAt), redeemCodeQueryVo.getCreatedAtEnd()) : null,
                                redeemCodeQueryVo.getUpdatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(RedeemCode_.updatedAt), redeemCodeQueryVo.getUpdatedAtBegin()) : null,
                                redeemCodeQueryVo.getUpdatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(RedeemCode_.updatedAt), redeemCodeQueryVo.getUpdatedAtEnd()) : null
                        )
                ).getRestriction(),
                redeemCodeQueryVo.getPageRequest()
        );
    }
}
