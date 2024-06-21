package com.zkg.tiktok.entity.user;

import lombok.Data;

import java.util.List;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-06-09
 * @Description:
 * @Version: 1.0
 */


@Data
public class Menu {

    private Long id;

    private Long pId;

    private String path;

    private String href;

    private String icon;

    private String title;

    private String target;

    private Integer isMenu;

    private List<Menu> child;
}
