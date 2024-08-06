/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.storage.domain.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@Component
@RequiredArgsConstructor
@Schema(description = "存储新增或编辑VO")
public class StorageAddOrEditVo {

    @Schema(description = "存储ID")
    private Long id;

    @Size(max = 100, message = "{ielts.domain.vo.StorageAddOrEditVo.title.Size}")
    @NotNull(message = "{ielts.domain.vo.StorageAddOrEditVo.title.NotNull}")
    @Schema(description = "存储标题")
    private String title;

    @Size(max = 10, message = "{ielts.domain.vo.StorageAddOrEditVo.fileType.Size}")
    @Schema(description = "文件类型（0:图片；1:音频；2:视频；3:markdown）")
    private int fileType;

    @NotNull(message = "{ielts.domain.vo.StorageAddOrEditVo.file.NotNull}")
    @Schema(description = "文件")
    private MultipartFile file;

}
