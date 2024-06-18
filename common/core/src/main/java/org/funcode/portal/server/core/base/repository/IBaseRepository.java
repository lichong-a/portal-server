/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.core.base.repository;

import org.funcode.portal.server.core.base.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@NoRepositoryBean
public interface IBaseRepository<T extends BaseEntity, I extends Serializable>
        extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {
}
