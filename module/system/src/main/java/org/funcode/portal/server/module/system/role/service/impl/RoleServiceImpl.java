/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.system.role.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.base.entity.BaseEntity;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.core.security.domain.dto.Role;
import org.funcode.portal.server.common.core.security.repository.IRoleRepository;
import org.funcode.portal.server.module.system.role.service.IRoleService;
import org.funcode.portal.server.module.system.role.vo.RoleQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements IRoleService {

    /**
     * repository.
     */
    private final IRoleRepository roleRepository;

    /**
     * init.
     *
     * @param repository user dao
     */
    public RoleServiceImpl(final IRoleRepository repository) {
        this.roleRepository = repository;
    }

    /**
     * @return base dao
     */
    @Override
    public IRoleRepository getBaseRepository() {
        return this.roleRepository;
    }

    /**
     * find by page.
     *
     * @param roleQueryVo query
     * @return page
     */
    @Override
    public Page<Role> findPage(RoleQueryVo roleQueryVo) {
        return this.getBaseRepository().findAll(
                (Specification<Role>) (root, query, criteriaBuilder) -> query.where(criteriaBuilder.and(
                                StringUtils.isNotBlank(roleQueryVo.getRoleKey()) ? criteriaBuilder.like(root.get(Role.ColumnName.ROLE_KEY), "%" + roleQueryVo.getRoleKey() + "%") : null,
                                StringUtils.isNotBlank(roleQueryVo.getRoleName()) ? criteriaBuilder.like(root.get(Role.ColumnName.ROLE_NAME), "%" + roleQueryVo.getRoleName() + "%") : null,
                                StringUtils.isNotBlank(roleQueryVo.getDescription()) ? criteriaBuilder.like(root.get(Role.ColumnName.DESCRIPTION), "%" + roleQueryVo.getDescription() + "%") : null,
                                roleQueryVo.getId() != null ? criteriaBuilder.equal(root.get(Role.ColumnName.ID), roleQueryVo.getId()) : null,
                                roleQueryVo.getCreatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(BaseEntity.ColumnName.CREATED_AT), roleQueryVo.getCreatedAtBegin()) : null,
                                roleQueryVo.getCreatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(BaseEntity.ColumnName.CREATED_AT), roleQueryVo.getCreatedAtEnd()) : null,
                                roleQueryVo.getUpdatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(BaseEntity.ColumnName.UPDATED_AT), roleQueryVo.getUpdatedAtBegin()) : null,
                                roleQueryVo.getUpdatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(BaseEntity.ColumnName.UPDATED_AT), roleQueryVo.getUpdatedAtEnd()) : null
                        )
                ).getRestriction(),
                roleQueryVo.getPageRequest()
        );
    }
}
