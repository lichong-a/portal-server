/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package com.eoi.portal.server.core.business.user.service.impl;

import com.eoi.portal.server.core.base.repository.IBaseRepository;
import com.eoi.portal.server.core.base.service.impl.BaseServiceImpl;
import com.eoi.portal.server.core.business.user.domain.User;
import com.eoi.portal.server.core.business.user.repository.IUserRepository;
import com.eoi.portal.server.core.business.user.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
     * userRepository.
     */
    private final IUserRepository userRepository;

    /**
     * init.
     *
     * @param userRepository2 user dao
     */
    public UserServiceImpl(final IUserRepository userRepository2) {
        this.userRepository = userRepository2;
    }

    /**
     * @return base dao
     */
    @Override
    public IBaseRepository<User, Long> getBaseRepository() {
        return this.userRepository;
    }

    /**
     * find by page.
     *
     * @param user        query
     * @param pageRequest pageRequest
     * @return page
     */
    @Override
    public Page<User> findPage(User user, PageRequest pageRequest) {
        return this.getBaseRepository().findAll(
                (Specification<User>) (root, query, criteriaBuilder) -> query.where(criteriaBuilder.and(
                                StringUtils.isNotBlank(user.getEmail()) ? criteriaBuilder.equal(root.get("email"), user.getEmail()) : null,
                                StringUtils.isNotBlank(user.getName()) ? criteriaBuilder.equal(root.get("name"), user.getName()) : null,
                                StringUtils.isNotBlank(user.getPhone()) ? criteriaBuilder.equal(root.get("phone"), user.getPhone()) : null,
                                StringUtils.isNotBlank(user.getPassword()) ? criteriaBuilder.equal(root.get("password"), user.getPassword()) : null,
                                user.getId() != null ? criteriaBuilder.equal(root.get("id"), user.getId()) : null,
                                user.getCreatedAt() != null ? criteriaBuilder.equal(root.get("createdAt"), user.getCreatedAt()) : null,
                                user.getUpdatedAt() != null ? criteriaBuilder.equal(root.get("updatedAt"), user.getUpdatedAt()) : null
                        )
                ).getRestriction(),
                pageRequest
        );
    }

}
