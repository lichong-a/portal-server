/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package com.eoi.portal.server.core.security.current.service;

import com.eoi.portal.server.core.base.service.IBaseService;
import com.eoi.portal.server.core.security.current.domain.dto.User;
import com.eoi.portal.server.core.security.current.domain.vo.UserVo;
import org.springframework.data.domain.Page;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
public interface IUserService extends IBaseService<User, Long> {

    /**
     * find by page.
     *
     * @param userVo query
     * @return page
     */
    Page<User> findPage(UserVo userVo);

    User findByEmail(String email);
}
