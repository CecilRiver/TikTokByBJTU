package com.zkg.tiktok.entity.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zkg.tiktok.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-06-09
 * @Description:
 * @Version: 1.0
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class Permission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long pId;

    private String path;

    private String href;

    private String icon;

    private String name;

    private Integer isMenu;

    private String target;

    private Integer sort;

    private Integer state;

    @TableField(exist = false)
    private List<Permission> children;


}