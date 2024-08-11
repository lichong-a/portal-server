/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.core.security.domain.vo.user.UserQueryVo;
import org.funcode.portal.server.common.core.security.repository.IUserRepository;
import org.funcode.portal.server.common.core.security.service.IUserService;
import org.funcode.portal.server.common.domain.security.User;
import org.funcode.portal.server.common.domain.security.User_;
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
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements IUserService {

    private final IUserRepository userRepository;

    /**
     * @return base dao
     */
    @Override
    public IUserRepository getBaseRepository() {
        return this.userRepository;
    }

    /**
     * find by page.
     *
     * @param userQueryVo query
     * @return page
     */
    @Override
    @Transactional
    public Page<User> findPage(UserQueryVo userQueryVo) {
        return getBaseRepository().findAll(
                (Specification<User>) (root, query, criteriaBuilder) -> query.where(criteriaBuilder.and(
                                StringUtils.isNotBlank(userQueryVo.getEmail()) ? criteriaBuilder.like(root.get(User_.email), "%" + userQueryVo.getEmail() + "%") : criteriaBuilder.conjunction(),
                                StringUtils.isNotBlank(userQueryVo.getUsername()) ? criteriaBuilder.like(root.get(User_.username), "%" + userQueryVo.getUsername() + "%") : criteriaBuilder.conjunction(),
                                StringUtils.isNotBlank(userQueryVo.getNickName()) ? criteriaBuilder.like(root.get(User_.nickName), "%" + userQueryVo.getNickName() + "%") : criteriaBuilder.conjunction(),
                                StringUtils.isNotBlank(userQueryVo.getRealName()) ? criteriaBuilder.like(root.get(User_.realName), "%" + userQueryVo.getRealName() + "%") : criteriaBuilder.conjunction(),
                                StringUtils.isNotBlank(userQueryVo.getPhone()) ? criteriaBuilder.like(root.get(User_.phone), "%" + userQueryVo.getPhone() + "%") : criteriaBuilder.conjunction(),
                                StringUtils.isNotBlank(userQueryVo.getWechatId()) ? criteriaBuilder.like(root.get(User_.wechatId), "%" + userQueryVo.getWechatId() + "%") : criteriaBuilder.conjunction(),
                                userQueryVo.getId() != null ? criteriaBuilder.equal(root.get(User_.id), userQueryVo.getId()) : criteriaBuilder.conjunction(),
                                userQueryVo.getCreatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(User_.createdAt), userQueryVo.getCreatedAtBegin()) : criteriaBuilder.conjunction(),
                                userQueryVo.getCreatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(User_.createdAt), userQueryVo.getCreatedAtEnd()) : criteriaBuilder.conjunction(),
                                userQueryVo.getUpdatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(User_.updatedAt), userQueryVo.getUpdatedAtBegin()) : criteriaBuilder.conjunction(),
                                userQueryVo.getUpdatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(User_.updatedAt), userQueryVo.getUpdatedAtEnd()) : criteriaBuilder.conjunction()
                        )
                ).getRestriction(),
                userQueryVo.getPageRequest()
        );
    }

    @Override
    @Transactional
    public User findByEmail(String email) {
        return getBaseRepository().findByEmail(email);
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return getBaseRepository().findByUsername(username);
    }

    @Override
    @Transactional
    public User findByWechatId(String wechatId) {
        return getBaseRepository().findByWechatId(wechatId);
    }

}
