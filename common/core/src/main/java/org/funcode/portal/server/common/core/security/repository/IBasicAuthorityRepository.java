/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.repository;

import org.funcode.portal.server.common.core.base.repository.IBaseRepository;
import org.funcode.portal.server.common.domain.security.BasicAuthority;
import org.springframework.stereotype.Repository;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Repository
public interface IBasicAuthorityRepository extends IBaseRepository<BasicAuthority, Long> {
}
