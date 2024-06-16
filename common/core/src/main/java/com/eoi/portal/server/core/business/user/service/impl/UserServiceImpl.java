/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package com.eoi.portal.server.core.business.user.service.impl;

import com.eoi.portal.server.core.base.service.impl.BaseServiceImpl;
import com.eoi.portal.server.core.business.user.domain.dto.User;
import com.eoi.portal.server.core.business.user.domain.vo.UserVo;
import com.eoi.portal.server.core.business.user.repository.IUserRepository;
import com.eoi.portal.server.core.business.user.service.IUserService;
import org.apache.commons.lang3.StringUtils;
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
     * userRepository.
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
     * @param userVo query
     * @return page
     */
    @Override
    public Page<User> findPage(UserVo userVo) {
        return this.getBaseRepository().findAll(
                (Specification<User>) (root, query, criteriaBuilder) -> query.where(criteriaBuilder.and(
                                StringUtils.isNotBlank(userVo.getEmail()) ? criteriaBuilder.equal(root.get("email"), userVo.getEmail()) : null,
                                StringUtils.isNotBlank(userVo.getUsername()) ? criteriaBuilder.equal(root.get("username"), userVo.getUsername()) : null,
                                StringUtils.isNotBlank(userVo.getNickName()) ? criteriaBuilder.equal(root.get("nick_name"), userVo.getNickName()) : null,
                                StringUtils.isNotBlank(userVo.getRealName()) ? criteriaBuilder.equal(root.get("real_name"), userVo.getRealName()) : null,
                                StringUtils.isNotBlank(userVo.getPhone()) ? criteriaBuilder.equal(root.get("phone"), userVo.getPhone()) : null,
                                userVo.getId() != null ? criteriaBuilder.equal(root.get("id"), userVo.getId()) : null,
                                userVo.getCreatedAt() != null ? criteriaBuilder.equal(root.get("created_at"), userVo.getCreatedAt()) : null,
                                userVo.getUpdatedAt() != null ? criteriaBuilder.equal(root.get("updated_at"), userVo.getUpdatedAt()) : null
                        )
                ).getRestriction(),
                userVo.getPageRequest()
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

}
