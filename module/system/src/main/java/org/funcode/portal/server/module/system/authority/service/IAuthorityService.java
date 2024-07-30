/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.system.authority.service;

import org.funcode.portal.server.common.core.base.service.IBaseService;
import org.funcode.portal.server.common.core.security.domain.dto.BasicAuthority;
import org.funcode.portal.server.module.system.authority.domain.vo.AuthorityQueryVo;
import org.springframework.data.domain.Page;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
public interface IAuthorityService extends IBaseService<BasicAuthority, Long> {

    Page<BasicAuthority> findPage(AuthorityQueryVo authorityQueryVo);
}
