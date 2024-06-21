package com.zkg.tiktok.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-06-09
 * @Description: 文件实体类
 * @Version: 1.0
 */


@Data
@EqualsAndHashCode(callSuper = false)
public class File extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 文件key
    private String fileKey;

    // 文件格式
    private String format;

    // 文件类型
    private String type;

    // 文件大小
    private Long size;

    // 发布者
    private Long userId;

}