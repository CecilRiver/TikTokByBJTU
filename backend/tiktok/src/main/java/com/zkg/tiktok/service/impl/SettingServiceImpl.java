package com.zkg.tiktok.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkg.tiktok.entity.Setting;
import com.zkg.tiktok.mapper.SettingMapper;
import com.zkg.tiktok.service.SettingService;
import org.springframework.stereotype.Service;


@Service
public class SettingServiceImpl extends ServiceImpl<SettingMapper, Setting> implements SettingService {

}
