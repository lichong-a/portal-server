/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.system.role.service;

import org.funcode.portal.server.common.core.base.service.IBaseService;
import org.funcode.portal.server.common.core.security.domain.dto.Role;
import org.funcode.portal.server.module.system.role.vo.RoleQueryVo;
import org.springframework.data.domain.Page;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
public interface IRoleService extends IBaseService<Role, Long> {

    Page<Role> findPage(RoleQueryVo roleQueryVo);
}
