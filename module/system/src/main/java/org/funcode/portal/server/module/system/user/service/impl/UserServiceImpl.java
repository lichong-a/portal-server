/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.system.user.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.base.entity.BaseEntity;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.core.security.domain.dto.User;
import org.funcode.portal.server.common.core.security.repository.IUserRepository;
import org.funcode.portal.server.module.system.user.domain.vo.UserQueryVo;
import org.funcode.portal.server.module.system.user.service.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements IUserService {

    /**
     * repository.
     */
    private final IUserRepository userRepository;

    /**
     * init.
     *
     * @param repository user dao
     */
    public UserServiceImpl(final IUserRepository repository) {
        this.userRepository = repository;
    }

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
    public Page<User> findPage(UserQueryVo userQueryVo) {
        return this.getBaseRepository().findAll(
                (Specification<User>) (root, query, criteriaBuilder) -> query.where(criteriaBuilder.and(
                                StringUtils.isNotBlank(userQueryVo.getEmail()) ? criteriaBuilder.like(root.get(User.ColumnName.EMAIL), "%" + userQueryVo.getEmail() + "%") : null,
                                StringUtils.isNotBlank(userQueryVo.getUsername()) ? criteriaBuilder.like(root.get(User.ColumnName.USERNAME), "%" + userQueryVo.getUsername() + "%") : null,
                                StringUtils.isNotBlank(userQueryVo.getNickName()) ? criteriaBuilder.like(root.get(User.ColumnName.NICK_NAME), "%" + userQueryVo.getNickName() + "%") : null,
                                StringUtils.isNotBlank(userQueryVo.getRealName()) ? criteriaBuilder.like(root.get(User.ColumnName.REAL_NAME), "%" + userQueryVo.getRealName() + "%") : null,
                                StringUtils.isNotBlank(userQueryVo.getPhone()) ? criteriaBuilder.like(root.get(User.ColumnName.PHONE), "%" + userQueryVo.getPhone() + "%") : null,
                                userQueryVo.getId() != null ? criteriaBuilder.equal(root.get(User.ColumnName.ID), userQueryVo.getId()) : null,
                                userQueryVo.getCreatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(BaseEntity.ColumnName.CREATED_AT), userQueryVo.getCreatedAtBegin()) : null,
                                userQueryVo.getCreatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(BaseEntity.ColumnName.CREATED_AT), userQueryVo.getCreatedAtEnd()) : null,
                                userQueryVo.getUpdatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(BaseEntity.ColumnName.UPDATED_AT), userQueryVo.getUpdatedAtBegin()) : null,
                                userQueryVo.getUpdatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(BaseEntity.ColumnName.UPDATED_AT), userQueryVo.getUpdatedAtEnd()) : null
                        )
                ).getRestriction(),
                userQueryVo.getPageRequest()
        );
    }

    /**
     * find by email.
     *
     * @param email email
     * @return User
     */
    @Override
    public User findByEmail(String email) {
        return this.getBaseRepository().findByEmail(email);
    }

    @Override
    public User findByUsername(String username) {
        return this.getBaseRepository().findByUsername(username);
    }

    @Override
    public User findByWechatId(String wechatId) {
        return this.getBaseRepository().findByWechatId(wechatId);
    }

}
