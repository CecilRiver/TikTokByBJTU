package com.zkg.tiktok.service.user.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkg.tiktok.entity.user.UserSubscribe;
import com.zkg.tiktok.mapper.user.UserSubscribeMapper;
import com.zkg.tiktok.service.user.UserSubscribeService;
import org.springframework.stereotype.Service;


@Service
public class UserSubscribeServiceImpl extends ServiceImpl<UserSubscribeMapper, UserSubscribe> implements UserSubscribeService {
}
