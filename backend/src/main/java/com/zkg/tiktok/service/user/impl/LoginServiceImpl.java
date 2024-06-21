package com.zkg.tiktok.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zkg.tiktok.constant.RedisConstant;
import com.zkg.tiktok.entity.Captcha;
import com.zkg.tiktok.entity.user.User;
import com.zkg.tiktok.entity.vo.FindPWVO;
import com.zkg.tiktok.entity.vo.RegisterVO;
import com.zkg.tiktok.exception.BaseException;
import com.zkg.tiktok.service.user.CaptchaService;
import com.zkg.tiktok.service.user.LoginService;
import com.zkg.tiktok.service.user.UserService;
import com.zkg.tiktok.util.RedisCacheUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-06-04
 * @Description: 业务层实现
 * @Version: 1.0
 */


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Override
    public User login(User user) {
        final String password = user.getPassword();
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        user = userService.getOne(wrapper.eq(User::getEmail, user.getEmail()));
        if (ObjectUtils.isEmpty(user)) {
            throw new BaseException("没有该账号");
        }

        if (!password.equals(user.getPassword())) {
            throw new BaseException("密码不一致");
        }

        return user;
    }

    @Override
    public Boolean checkCode(String email, Integer code) {
        if (ObjectUtils.isEmpty(email) || ObjectUtils.isEmpty(code)) {
            throw new BaseException("参数为空");
        }
        final Object o = redisCacheUtil.get(RedisConstant.EMAIL_CODE + email);

        if (!code.toString().equals(o)) {
            throw new BaseException("验证码不正确");

        }
        return true;
    }

    @Override
    public void captcha(String uuId, HttpServletResponse response) throws IOException {
        if (ObjectUtils.isEmpty(uuId)) throw new IllegalArgumentException("uuid不能为空");
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        BufferedImage image = captchaService.getCaptcha(uuId);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    @Override
    public Boolean getCode(Captcha captcha) throws Exception {

        return captchaService.validate(captcha);
    }

    @Override
    public Boolean register(RegisterVO registerVO) throws Exception {
        // 注册成功后删除图形验证码
        if (userService.register(registerVO)) {
            captchaService.removeById(registerVO.getUuid());
            return true;
        }
        return false;
    }

    @Override
    public Boolean findPassword(FindPWVO findPWVO) {
        final Boolean b = userService.findPassword(findPWVO);
        return b;
    }
}