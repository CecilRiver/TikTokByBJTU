package com.zkg.tiktok.entity.vo;

import lombok.Data;
import com.zkg.tiktok.entity.user.Follow;


@Data
public class FollowVO extends Follow {

    private String nickName;
}
