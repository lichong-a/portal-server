/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.storage.service.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.qcloud.cos.transfer.Upload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.funcode.portal.server.common.core.base.exception.BusinessException;
import org.funcode.portal.server.common.core.base.service.impl.BaseServiceImpl;
import org.funcode.portal.server.common.core.config.COSConfig;
import org.funcode.portal.server.common.core.util.FileUtils;
import org.funcode.portal.server.common.domain.ielts.Storage;
import org.funcode.portal.server.common.domain.ielts.Storage_;
import org.funcode.portal.server.module.ielts.storage.domain.vo.StorageAddOrEditVo;
import org.funcode.portal.server.module.ielts.storage.domain.vo.StorageQueryVo;
import org.funcode.portal.server.module.ielts.storage.repository.IStorageRepository;
import org.funcode.portal.server.module.ielts.storage.service.IStorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StorageServiceImpl extends BaseServiceImpl<Storage, Long> implements IStorageService {

    private final IStorageRepository storageRepository;
    private final COSClient cosClient;
    private final COSConfig cosConfig;
    private final TransferManager transferManager;

    /**
     * @return base dao
     */
    @Override
    public IStorageRepository getBaseRepository() {
        return this.storageRepository;
    }

    @Override
    public Boolean upload(StorageAddOrEditVo storageAddOrEditVo) {
        try {
            File localFile = FileUtils.multipartFileToFile(storageAddOrEditVo.getFile());
            // 设置高级接口的配置项
            // 分块上传阈值和分块大小分别设置为 5MB 和 1MB（若不特殊设置，分块上传阈值和分块大小的默认值均为5MB）
            TransferManagerConfiguration transferManagerConfiguration = new TransferManagerConfiguration();
            transferManagerConfiguration.setMultipartUploadThreshold(5 * 1024 * 1024);
            transferManagerConfiguration.setMinimumUploadPartSize(5 * 1024 * 1024);
            transferManager.setConfiguration(transferManagerConfiguration);

            PutObjectRequest putObjectRequest = new PutObjectRequest(cosConfig.getBucketName(), storageAddOrEditVo.getTitle() + System.currentTimeMillis(), localFile);
            // 设置存储类型（如有需要，不需要请忽略此行代码）, 默认是标准(Standard), 低频(standard_ia)
            // 更多存储类型请参见 https://cloud.tencent.com/document/product/436/33417
            putObjectRequest.setStorageClass(StorageClass.Standard);
            //若需要设置对象的自定义 Headers 可参照下列代码,若不需要可省略下面这几行,对象自定义 Headers 的详细信息可参考 https://cloud.tencent.com/document/product/436/13361
            ObjectMetadata objectMetadata = new ObjectMetadata();
            //若设置 Content-Type、Cache-Control、Content-Disposition、Content-Encoding、Expires 这五个字自定义 Headers，推荐采用 objectMetadata.setHeader()
            //自定义header尽量避免特殊字符，使用中文前请先手动对其进行URLEncode
            objectMetadata.setHeader("title", URLEncoder.encode(storageAddOrEditVo.getTitle(), StandardCharsets.UTF_8));
            objectMetadata.setHeader("fileType", storageAddOrEditVo.getFileType());
            //若要设置 “x-cos-meta-[自定义后缀]” 这样的自定义 Header，推荐采用
            Map<String, String> userMeta = new HashMap<>();
            userMeta.put("x-cos-meta-author", "lichong.work");
            objectMetadata.setUserMetadata(userMeta);
            putObjectRequest.withMetadata(objectMetadata);

            // 高级接口会返回一个异步结果Upload
            // 可同步地调用 waitForUploadResult 方法等待上传完成，成功返回 UploadResult, 失败抛出异常
            Upload upload = transferManager.upload(putObjectRequest);
            UploadResult uploadResult = upload.waitForUploadResult();

            Storage storage = Storage.builder()
                    .bucketName(uploadResult.getBucketName())
                    .key(uploadResult.getKey())
                    .versionId(uploadResult.getVersionId())
                    .fileType(storageAddOrEditVo.getFileType())
                    .title(storageAddOrEditVo.getTitle())
                    .build();
            getBaseRepository().save(storage);
        } catch (Exception e) {
            log.error("上传文件失败，IO异常", e);
            throw new BusinessException("上传文件失败");
        }
        return true;
    }

    @Override
    public Boolean deleteStorage(Long storageId) {
        Storage storage = getBaseRepository().findById(storageId).orElseThrow(() -> new BusinessException("存储不存在"));
        try {
            cosClient.deleteObject(cosConfig.getBucketName(), storage.getKey());
        } catch (Exception e) {
            log.error("删除文件失败", e);
            throw new BusinessException("删除文件失败");
        }
        return true;
    }

    @Override
    public Page<Storage> findPage(StorageQueryVo storageQueryVo) {
        return this.findAll(
                (Specification<Storage>) (root, query, criteriaBuilder) -> query.where(criteriaBuilder.and(
                                StringUtils.isNotBlank(storageQueryVo.getBucketName()) ? criteriaBuilder.like(root.get(Storage_.bucketName), "%" + storageQueryVo.getBucketName() + "%") : null,
                                StringUtils.isNotBlank(storageQueryVo.getTitle()) ? criteriaBuilder.like(root.get(Storage_.title), "%" + storageQueryVo.getTitle() + "%") : null,
                                StringUtils.isNotBlank(storageQueryVo.getKey()) ? criteriaBuilder.like(root.get(Storage_.key), "%" + storageQueryVo.getKey() + "%") : null,
                                storageQueryVo.getId() != null ? criteriaBuilder.equal(root.get(Storage_.id), storageQueryVo.getId()) : null,
                                storageQueryVo.getFileType() != null ? criteriaBuilder.equal(root.get(Storage_.fileType), storageQueryVo.getFileType()) : null,
                                storageQueryVo.getVersionId() != null ? criteriaBuilder.equal(root.get(Storage_.versionId), storageQueryVo.getVersionId()) : null,
                                storageQueryVo.getCreatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(Storage_.createdAt), storageQueryVo.getCreatedAtBegin()) : null,
                                storageQueryVo.getCreatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(Storage_.createdAt), storageQueryVo.getCreatedAtEnd()) : null,
                                storageQueryVo.getUpdatedAtBegin() != null ? criteriaBuilder.greaterThanOrEqualTo(root.get(Storage_.updatedAt), storageQueryVo.getUpdatedAtBegin()) : null,
                                storageQueryVo.getUpdatedAtEnd() != null ? criteriaBuilder.lessThanOrEqualTo(root.get(Storage_.updatedAt), storageQueryVo.getUpdatedAtEnd()) : null
                        )
                ).getRestriction(),
                storageQueryVo.getPageRequest()
        );
    }
}
