/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.service;

import org.funcode.portal.server.common.core.base.service.IBaseService;
import org.funcode.portal.server.common.core.security.domain.vo.authority.AuthorityAddOrEditVo;
import org.funcode.portal.server.common.core.security.domain.vo.authority.AuthorityQueryVo;
import org.funcode.portal.server.common.domain.security.BasicAuthority;
import org.springframework.data.domain.Page;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
public interface IAuthorityService extends IBaseService<BasicAuthority, Long> {

    /**
     * 分页查询权限信息
     *
     * @param authorityQueryVo 查询参数
     * @return 分页结果
     */
    Page<BasicAuthority> findPage(AuthorityQueryVo authorityQueryVo);

    /**
     * 新增或编辑权限
     *
     * @param authorityAddOrEditVo 参数
     * @return 结果
     */
    BasicAuthority addOrEditAuthority(AuthorityAddOrEditVo authorityAddOrEditVo);
}
