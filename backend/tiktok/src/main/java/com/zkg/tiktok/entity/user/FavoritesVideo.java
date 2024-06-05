package com.zkg.tiktok.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-06-04
 * @Description: 收藏视频
 * @Version: 1.0
 */


@Data
@EqualsAndHashCode(callSuper = false)
public class FavoritesVideo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long favoritesId;

    private Long videoId;

    // 冗余字段
    private Long userId;

}