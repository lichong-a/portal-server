/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.system.authority.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.core.security.repository.IBasicAuthorityRepository;
import org.funcode.portal.server.common.domain.base.BaseEntity;
import org.funcode.portal.server.common.domain.security.BasicAuthority;
import org.funcode.portal.server.module.system.authority.domain.vo.AuthorityAddOrEditVo;
import org.funcode.portal.server.module.system.authority.domain.vo.AuthorityQueryVo;
import org.funcode.portal.server.module.system.authority.service.IAuthorityService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl extends BaseServiceImpl<BasicAuthority, Long> implements IAuthorityService {

    private final IBasicAuthorityRepository basicAuthorityRepository;

    /**
     * @return base dao
     */
    @Override
    public IBasicAuthorityRepository getBaseRepository() {
        return this.basicAuthorityRepository;
    }

    /**
     * find by page.
     *
     * @param authorityQueryVo query
     * @return page
     */
    @Override
    @Transactional
    public Page<BasicAuthority> findPage(AuthorityQueryVo authorityQueryVo) {
        return this.getBaseRepository().findAll(
                (Specification<BasicAuthority>) (root, query, criteriaBuilder) -> query.where(criteriaBuilder.and(
                                StringUtils.isNotBlank(authorityQueryVo.getAuthorityKey()) ? criteriaBuilder.like(root.get(BasicAuthority.ColumnName.AUTHORITY_KEY), "%" + authorityQueryVo.getAuthorityKey() + "%") : null,
                                StringUtils.isNotBlank(authorityQueryVo.getAuthorityName()) ? criteriaBuilder.like(root.get(BasicAuthority.ColumnName.AUTHORITY_NAME), "%" + authorityQueryVo.getAuthorityName() + "%") : null,
                                StringUtils.isNotBlank(authorityQueryVo.getDescription()) ? criteriaBuilder.like(root.get(BasicAuthority.ColumnName.DESCRIPTION), "%" + authorityQueryVo.getDescription() + "%") : null,
                                authorityQueryVo.getId() != null ? criteriaBuilder.equal(root.get(BasicAuthority.ColumnName.ID), authorityQueryVo.getId()) : null,
                                authorityQueryVo.getCreatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(BaseEntity.ColumnName.CREATED_AT), authorityQueryVo.getCreatedAtBegin()) : null,
                                authorityQueryVo.getCreatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(BaseEntity.ColumnName.CREATED_AT), authorityQueryVo.getCreatedAtEnd()) : null,
                                authorityQueryVo.getUpdatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(BaseEntity.ColumnName.UPDATED_AT), authorityQueryVo.getUpdatedAtBegin()) : null,
                                authorityQueryVo.getUpdatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(BaseEntity.ColumnName.UPDATED_AT), authorityQueryVo.getUpdatedAtEnd()) : null
                        )
                ).getRestriction(),
                authorityQueryVo.getPageRequest()
        );
    }

    @Override
    @Transactional
    public boolean addOrEditAuthority(AuthorityAddOrEditVo authorityAddOrEditVo) {
        BasicAuthority basicAuthority = authorityAddOrEditVo.transToBasicAuthority();
        this.getBaseRepository().save(basicAuthority);
        return true;
    }
}
