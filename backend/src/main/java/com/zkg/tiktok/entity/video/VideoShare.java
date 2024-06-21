package com.zkg.tiktok.entity.video;

import com.zkg.tiktok.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-06-09
 * @Description:
 * @Version: 1.0
 */


@Data
@EqualsAndHashCode(callSuper = false)
public class VideoShare extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long videoId;

    private Long userId;

    private String ip;

}
