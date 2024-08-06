/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.storage.service;

import org.funcode.portal.server.common.core.base.service.IBaseService;
import org.funcode.portal.server.common.domain.ielts.Storage;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
public interface IStorageService extends IBaseService<Storage, Long> {

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 上传结果
     */
    Boolean upload(MultipartFile file);
}
