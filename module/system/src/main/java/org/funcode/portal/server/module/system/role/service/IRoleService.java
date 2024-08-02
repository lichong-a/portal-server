/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.system.role.service;

import org.funcode.portal.server.common.core.base.service.IBaseService;
import org.funcode.portal.server.common.core.security.domain.dto.Role;
import org.funcode.portal.server.module.system.role.vo.RoleAddOrEditVo;
import org.funcode.portal.server.module.system.role.vo.RoleQueryVo;
import org.springframework.data.domain.Page;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
public interface IRoleService extends IBaseService<Role, Long> {

    /**
     * 分页查询角色信息
     *
     * @param roleQueryVo 查询参数
     * @return 分页结果
     */
    Page<Role> findPage(RoleQueryVo roleQueryVo);

    /**
     * 新增或编辑角色
     *
     * @param roleAddOrEditVo 参数
     * @return 结果
     */
    boolean addOrEditRole(RoleAddOrEditVo roleAddOrEditVo);

}
