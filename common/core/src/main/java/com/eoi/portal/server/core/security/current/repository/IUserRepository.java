/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package com.eoi.portal.server.core.security.current.repository;

import com.eoi.portal.server.core.base.repository.IBaseRepository;
import com.eoi.portal.server.core.security.current.domain.dto.User;
import org.springframework.stereotype.Repository;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Repository
public interface IUserRepository extends IBaseRepository<User, Long> {
    User findByEmail(String email);
}
