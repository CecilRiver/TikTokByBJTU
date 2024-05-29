package com.zkg.tiktok.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-05-29
 * @Description: 用户角色对应
 * @Version: 1.0
 */


@Data
@EqualsAndHashCode(callSuper = false)
public class UserRole   implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long roleId;

    private Long userId;


}
