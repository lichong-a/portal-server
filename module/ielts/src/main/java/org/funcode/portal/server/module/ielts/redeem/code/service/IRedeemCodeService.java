/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.redeem.code.service;

import org.funcode.portal.server.common.core.base.service.IBaseService;
import org.funcode.portal.server.common.domain.ielts.RedeemCode;
import org.funcode.portal.server.module.ielts.redeem.code.domain.vo.RedeemCodeAddVo;
import org.funcode.portal.server.module.ielts.redeem.code.domain.vo.RedeemCodeQueryVo;
import org.springframework.data.domain.Page;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
public interface IRedeemCodeService extends IBaseService<RedeemCode, Long> {

    /**
     * 兑换码兑换
     *
     * @param code 兑换码
     * @return 兑换结果
     */
    RedeemCode redeem(String code);

    /**
     * 新增兑换码
     *
     * @param redeemCodeAddVo 兑换码VO
     * @return 兑换码
     */
    RedeemCode addRedeemCode(RedeemCodeAddVo redeemCodeAddVo);

    /**
     * 作废兑换码
     *
     * @param redeemCodeId 兑换码ID
     * @return 兑换码
     */
    RedeemCode invalidRedeemCode(long redeemCodeId);

    /**
     * 删除兑换码
     *
     * @param code 兑换码
     * @return 是否删除成功
     */
    Boolean deleteRedeemCode(String code);

    /**
     * 分页查询兑换码信息
     *
     * @param redeemCodeQueryVo 查询参数
     * @return 分页结果
     */
    Page<RedeemCode> pageList(RedeemCodeQueryVo redeemCodeQueryVo);

}
