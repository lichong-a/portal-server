/*
 * Copyright 2024 李冲. All rights reserved.
 *
 */

package org.funcode.portal.server.module.ielts.storage.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.funcode.portal.server.common.domain.base.PageRequestVo;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

/**
 * @author 李冲
 * @see <a href="https://lichong.work">李冲博客</a>
 * @since 0.0.1
 */
@Data
@Schema(description = "存储文件查询条件VO")
public class StorageQueryVo {
    @Schema(description = "存储ID（精确）")
    private Long id;
    @Size(max = 100, message = "{ielts.domain.vo.StorageQueryVo.title.Size}")
    @Schema(description = "存储标题（模糊）")
    private String title;
    @Schema(description = "文件类型（0:图片；1:音频；2:视频；3:markdown）（精确）")
    private Integer fileType;
    @Size(max = 200, message = "{ielts.domain.vo.StorageQueryVo.bucketName.Size}")
    @Schema(description = "桶名称（模糊）")
    private String bucketName;
    @Size(max = 300, message = "{ielts.domain.vo.StorageQueryVo.keySize}")
    @Schema(description = "唯一标识（模糊）")
    private String key;
    @Size(max = 200, message = "{ielts.domain.vo.StorageQueryVo.versionId.Size}")
    @Schema(description = "版本ID（精确）")
    private String versionId;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Schema(description = "创建时间的开始时间", type = "string", format = "date", example = "2024-01-01 01:01:00")
    private LocalDateTime createdAtBegin;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Schema(description = "创建时间的结束时间", type = "string", format = "date", example = "2024-01-01 01:01:00")
    private LocalDateTime createdAtEnd;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Schema(description = "更新时间的开始时间", type = "string", format = "date", example = "2024-01-01 01:01:00")
    private LocalDateTime updatedAtBegin;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Schema(description = "更新时间的结束时间", type = "string", format = "date", example = "2024-01-01 01:01:00")
    private LocalDateTime updatedAtEnd;
    @Schema(description = "分页参数")
    private PageRequestVo pageRequestVo;

    @Schema(hidden = true)
    public PageRequest getPageRequest() {
        return PageRequest.of(pageRequestVo.getCurrent() - 1, pageRequestVo.getPageSize());
    }

}
