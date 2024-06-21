package com.zkg.tiktok.entity.response;

import lombok.Data;
import lombok.ToString;
import com.zkg.tiktok.entity.task.VideoTask;
import com.zkg.tiktok.entity.video.Video;

/**
 * @description: 封装视频审核返回结果
 * @Author: Xhy
 * @CreateTime: 2023-10-29 14:40
 */
@Data
@ToString
public class VideoAuditResponse {

    private AuditResponse videoAuditResponse;

    private AuditResponse imageAuditResponse;

    private AuditResponse textAuditResponse;

    private VideoTask videoTask;
}
