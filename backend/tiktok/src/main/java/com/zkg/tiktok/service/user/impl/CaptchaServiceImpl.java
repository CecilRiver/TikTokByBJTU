package com.zkg.tiktok.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.code.kaptcha.Producer;
import com.zkg.tiktok.constant.RedisConstant;
import com.zkg.tiktok.entity.Captcha;
import com.zkg.tiktok.exception.BaseException;
import com.zkg.tiktok.mapper.CaptchaMapper;
import com.zkg.tiktok.service.user.CaptchaService;
import com.zkg.tiktok.service.user.EmailService;
import com.zkg.tiktok.util.DateUtil;
import com.zkg.tiktok.util.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Date;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-06-04
 * @Description: 验证码业务层实现
 * @Version: 1.0
 */


@Service
public class CaptchaServiceImpl extends ServiceImpl<CaptchaMapper, Captcha> implements CaptchaService {


    @Autowired
    private Producer producer;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Override
    public BufferedImage getCaptcha(String uuId) {
        String code = this.producer.createText();
        Captcha captcha = new Captcha();
        captcha.setUuid(uuId);
        captcha.setCode(code);
        captcha.setExpireTime(DateUtil.addDateMinutes(new Date(),5));
        this.save(captcha);
        return producer.createImage(code);
    }

    @Override
    public boolean validate(Captcha captcha) throws Exception {
        String email = captcha.getEmail();
        final String code1 = captcha.getCode();
        captcha = this.getOne(new LambdaQueryWrapper<Captcha>().eq(Captcha::getUuid, captcha.getUuid()));
        if (captcha == null) throw new BaseException("uuId为空");

        this.remove(new LambdaQueryWrapper<Captcha>().eq(Captcha::getUuid, captcha.getUuid()));
        if(!captcha.getCode().equals(code1)){
            throw new BaseException("code错误");
        }
        if(captcha.getExpireTime().getTime()<=System.currentTimeMillis()){
            throw new BaseException("uuid过期");
        }
        if (!code1.equals(captcha.getCode())){
            return false;
        }

        String code = getSixCode();
        redisCacheUtil.set(RedisConstant.EMAIL_CODE+email,code,RedisConstant.EMAIL_CODE_TIME);
        emailService.send(email,"注册验证码:"+code+",验证码5分钟之内有效");
        return true;
    }


    public static String getSixCode(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int code = (int) (Math.random()*10);
            builder.append(code);
        }
        return builder.toString();
    }
}