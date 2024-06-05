package com.zkg.tiktok.controller;

import com.zkg.tiktok.entity.Captcha;
import com.zkg.tiktok.entity.user.User;
import com.zkg.tiktok.entity.vo.FindPWVO;
import com.zkg.tiktok.entity.vo.RegisterVO;
import com.zkg.tiktok.service.user.LoginService;
import com.zkg.tiktok.util.JwtUtils;
import com.zkg.tiktok.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-06-04
 * @Description: 登录接口
 * @Version: 1.0
 */

@Api(tags = "用户登录接口")
@RestController
@RequestMapping("/tiktok/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 登录
     * @param user
     * @return
     */
    @ApiOperation(value = "用户登录", notes = "用户登录接口，返回token")
    @PostMapping
    public R login(@RequestBody @Validated User user) {
        user = loginService.login(user);
        // 登录成功，生成token
        String token = JwtUtils.getJwtToken(user.getId(), user.getNickName());
        final HashMap<Object, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("name", user.getNickName());
        map.put("user", user);
        return R.ok().data(map);
    }

    /**
     * 获取图形验证码
     * @param response
     * @param uuId
     * @throws IOException
     */
    @ApiOperation(value = "获取图形验证码", notes = "根据UUID获取图形验证码")
    @GetMapping("/captcha.jpg/{uuId}")
    public void captcha(HttpServletResponse response, @PathVariable String uuId) throws IOException {
        loginService.captcha(uuId, response);
    }

    /**
     * 获取验证码
     * @param captcha
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取验证码", notes = "获取短信或邮箱验证码")
    @PostMapping("/getCode")
    public R getCode(@RequestBody @Validated Captcha captcha) throws Exception {
        if (!loginService.getCode(captcha)) {
            return R.error().message("验证码错误");
        }
        return R.ok().message("发送成功,请耐心等待");
    }

    /**
     * 检测邮箱验证码
     * @param email
     * @param code
     * @return
     */
    @ApiOperation(value = "检测邮箱验证码", notes = "验证用户输入的邮箱验证码是否正确")
    @PostMapping("/check")
    public R check(String email, Integer code) {
        loginService.checkCode(email, code);
        return R.ok().message("验证成功");
    }

    /**
     * 注册
     * @param registerVO
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "用户注册", notes = "用户注册接口，提供注册信息和验证码")
    @PostMapping("/register")
    public R register(@RequestBody @Validated RegisterVO registerVO) throws Exception {
        if (!loginService.register(registerVO)) {
            return R.error().message("注册失败,验证码错误");
        }
        return R.ok().message("注册成功");
    }

    /**
     * 找回密码
     * @param findPWVO
     * @return
     */
    @ApiOperation(value = "找回密码", notes = "通过验证码找回密码")
    @PostMapping("/findPassword")
    public R findPassword(@RequestBody @Validated FindPWVO findPWVO, HttpServletResponse response) {
        final Boolean b = loginService.findPassword(findPWVO);
        return R.ok().message(b ? "修改成功" : "修改失败,验证码不正确");
    }
}
