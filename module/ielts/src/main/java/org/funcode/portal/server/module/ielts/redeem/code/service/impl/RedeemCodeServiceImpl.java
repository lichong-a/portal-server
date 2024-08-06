/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.redeem.code.service.impl;

import lombok.RequiredArgsConstructor;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.domain.ielts.RedeemCode;
import org.funcode.portal.server.module.ielts.redeem.code.repository.IRedeemCodeRepository;
import org.funcode.portal.server.module.ielts.redeem.code.service.IRedeemCodeService;
import org.springframework.stereotype.Service;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class RedeemCodeServiceImpl extends BaseServiceImpl<RedeemCode, Long> implements IRedeemCodeService {

    private final IRedeemCodeRepository redeemCodeRepository;

    /**
     * @return base dao
     */
    @Override
    public IRedeemCodeRepository getBaseRepository() {
        return this.redeemCodeRepository;
    }
}
