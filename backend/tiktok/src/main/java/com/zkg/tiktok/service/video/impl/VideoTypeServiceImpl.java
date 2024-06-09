package com.zkg.tiktok.service.video.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkg.tiktok.entity.video.VideoType;
import com.zkg.tiktok.mapper.video.VideoTypeMapper;
import com.zkg.tiktok.service.video.VideoTypeService;
import org.springframework.stereotype.Service;


@Service
public class VideoTypeServiceImpl extends ServiceImpl<VideoTypeMapper, VideoType> implements VideoTypeService {

}
