/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.service;

import org.funcode.portal.server.common.core.base.service.IBaseService;
import org.funcode.portal.server.common.core.security.domain.vo.user.UserQueryVo;
import org.funcode.portal.server.common.domain.security.User;
import org.springframework.data.domain.Page;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
public interface IUserService extends IBaseService<User, Long> {

    Page<User> findPage(UserQueryVo userQueryVo);

    User findByEmail(String email);

    User findByUsername(String username);

    User findByWechatId(String wechatId);
}
