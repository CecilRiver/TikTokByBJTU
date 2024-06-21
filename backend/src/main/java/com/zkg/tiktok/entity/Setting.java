package com.zkg.tiktok.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zkg.tiktok.entity.json.SettingScoreJson;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-06-09
 * @Description: 系统设置
 * @Version: 1.0
 */


@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_setting")
public class Setting implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    // 审核策略
    private String auditPolicy;

    // 热门视频热度限制
    private Double hotLimit;

    // 审核开关
    private Boolean auditOpen;

    // 资源放行ip
    private String allowIp;

    private Boolean auth;

    @TableField(exist = false)
    private SettingScoreJson settingScoreJson;

}