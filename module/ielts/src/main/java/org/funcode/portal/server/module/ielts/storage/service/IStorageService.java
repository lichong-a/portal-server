/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.storage.service;

import org.funcode.portal.server.common.core.base.service.IBaseService;
import org.funcode.portal.server.common.domain.ielts.Storage;
import org.funcode.portal.server.module.ielts.storage.domain.vo.StorageAddOrEditVo;
import org.funcode.portal.server.module.ielts.storage.domain.vo.StorageQueryVo;
import org.springframework.data.domain.Page;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
public interface IStorageService extends IBaseService<Storage, Long> {

    /**
     * 上传文件
     *
     * @param storageAddOrEditVo 参数
     * @return 上传结果
     */
    Storage upload(StorageAddOrEditVo storageAddOrEditVo);

    /**
     * 删除文件
     *
     * @param storageId 文件ID
     * @return 是否删除成功
     */
    Boolean deleteStorage(Long storageId);

    /**
     * 分页查询
     *
     * @param storageQueryVo 查询条件
     * @return 分页查询结果
     */
    Page<Storage> findPage(StorageQueryVo storageQueryVo);
}
