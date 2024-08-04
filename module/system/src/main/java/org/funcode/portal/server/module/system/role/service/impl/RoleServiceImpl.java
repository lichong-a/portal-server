/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.system.role.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.base.entity.BaseEntity;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.core.security.domain.dto.Role;
import org.funcode.portal.server.common.core.security.repository.IRoleRepository;
import org.funcode.portal.server.module.system.authority.service.IAuthorityService;
import org.funcode.portal.server.module.system.role.service.IRoleService;
import org.funcode.portal.server.module.system.role.vo.RoleAddOrEditVo;
import org.funcode.portal.server.module.system.role.vo.RoleQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements IRoleService {

    private final IRoleRepository roleRepository;
    private final IAuthorityService authorityService;


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
    @Transactional
    public Page<Role> findPage(RoleQueryVo roleQueryVo) {
        return this.findAll(
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

    @Override
    @Transactional
    public boolean addOrEditRole(RoleAddOrEditVo roleAddOrEditVo) {
        Role role = transAddOrEditVoToRole(roleAddOrEditVo);
        this.save(role);
        return true;
    }

    /**
     * VO转换为DTO
     * @param roleAddOrEditVo 新增或编辑的参数
     * @return 转换结果
     */
    public Role transAddOrEditVoToRole(RoleAddOrEditVo roleAddOrEditVo) {
        Role role = new Role();
        BeanUtils.copyProperties(this, role);
        role.setBasicAuthorities(new HashSet<>(authorityService.findList(roleAddOrEditVo.getAuthorityIds())));
        return role;
    }
}
