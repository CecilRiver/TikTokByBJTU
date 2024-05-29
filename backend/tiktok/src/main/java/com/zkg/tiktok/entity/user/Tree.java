package com.zkg.tiktok.entity.user;

import lombok.Data;

import java.util.List;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-05-29
 * @Description: 树
 * @Version: 1.0
 */


@Data
public class Tree {

    private Long id;

    private Long pId;

    private String title;

    private List<Tree> children;

    private Boolean spread;
}
