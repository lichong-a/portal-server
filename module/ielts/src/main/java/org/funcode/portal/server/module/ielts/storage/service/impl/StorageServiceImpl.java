/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.storage.service.impl;

import lombok.RequiredArgsConstructor;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.core.module.ielts.domain.Storage;
import org.funcode.portal.server.module.ielts.storage.repository.IStorageRepository;
import org.funcode.portal.server.module.ielts.storage.service.IStorageService;
import org.springframework.stereotype.Service;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Service
@RequiredArgsConstructor
public class StorageServiceImpl extends BaseServiceImpl<Storage, Long> implements IStorageService {

    private final IStorageRepository storageRepository;

    /**
     * @return base dao
     */
    @Override
    public IStorageRepository getBaseRepository() {
        return this.storageRepository;
    }
}
