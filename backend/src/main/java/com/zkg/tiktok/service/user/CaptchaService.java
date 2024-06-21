package com.zkg.tiktok.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zkg.tiktok.entity.Captcha;

import java.awt.image.BufferedImage;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-06-04
 * @Description: 系统验证码
 * @Version: 1.0
 */
public interface CaptchaService extends IService<Captcha> {


    BufferedImage getCaptcha(String uuId);

    boolean validate(Captcha captcha) throws Exception;
}
