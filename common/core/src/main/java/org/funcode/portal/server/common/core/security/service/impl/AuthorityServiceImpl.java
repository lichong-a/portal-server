/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.common.core.security.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.core.security.domain.vo.authority.AuthorityAddOrEditVo;
import org.funcode.portal.server.common.core.security.domain.vo.authority.AuthorityQueryVo;
import org.funcode.portal.server.common.core.security.repository.IBasicAuthorityRepository;
import org.funcode.portal.server.common.core.security.service.IAuthorityService;
import org.funcode.portal.server.common.domain.security.BasicAuthority;
import org.funcode.portal.server.common.domain.security.BasicAuthority_;
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
        return getBaseRepository().findAll(
                (Specification<BasicAuthority>) (root, query, criteriaBuilder) -> query.where(criteriaBuilder.and(
                                StringUtils.isNotBlank(authorityQueryVo.getAuthorityKey()) ? criteriaBuilder.like(root.get(BasicAuthority_.authorityKey), "%" + authorityQueryVo.getAuthorityKey() + "%") : criteriaBuilder.conjunction(),
                                StringUtils.isNotBlank(authorityQueryVo.getAuthorityName()) ? criteriaBuilder.like(root.get(BasicAuthority_.authorityName), "%" + authorityQueryVo.getAuthorityName() + "%") : criteriaBuilder.conjunction(),
                                StringUtils.isNotBlank(authorityQueryVo.getDescription()) ? criteriaBuilder.like(root.get(BasicAuthority_.description), "%" + authorityQueryVo.getDescription() + "%") : criteriaBuilder.conjunction(),
                                authorityQueryVo.getId() != null ? criteriaBuilder.equal(root.get(BasicAuthority_.id), authorityQueryVo.getId()) : criteriaBuilder.conjunction(),
                                authorityQueryVo.getCreatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(BasicAuthority_.createdAt), authorityQueryVo.getCreatedAtBegin()) : criteriaBuilder.conjunction(),
                                authorityQueryVo.getCreatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(BasicAuthority_.createdAt), authorityQueryVo.getCreatedAtEnd()) : criteriaBuilder.conjunction(),
                                authorityQueryVo.getUpdatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(BasicAuthority_.updatedAt), authorityQueryVo.getUpdatedAtBegin()) : criteriaBuilder.conjunction(),
                                authorityQueryVo.getUpdatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(BasicAuthority_.updatedAt), authorityQueryVo.getUpdatedAtEnd()) : criteriaBuilder.conjunction()
                        )
                ).getRestriction(),
                authorityQueryVo.getPageRequest()
        );
    }

    @Override
    @Transactional
    public BasicAuthority addOrEditAuthority(AuthorityAddOrEditVo authorityAddOrEditVo) {
        BasicAuthority basicAuthority = authorityAddOrEditVo.transToBasicAuthority();
        return getBaseRepository().save(basicAuthority);
    }
}
