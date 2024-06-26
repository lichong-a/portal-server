/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.system.user.service;

import org.funcode.portal.server.common.core.base.service.IBaseService;
import org.funcode.portal.server.common.core.security.domain.dto.User;
import org.funcode.portal.server.module.system.user.domain.vo.UserVo;
import org.springframework.data.domain.Page;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
public interface IUserService extends IBaseService<User, Long> {

    Page<User> findPage(UserVo userVo);

    User findByEmail(String email);

    User findByUsername(String username);
}
