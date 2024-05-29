package com.zkg.tiktok.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zkg.tiktok.entity.user.User;
import com.zkg.tiktok.service.user.UserService;
import com.zkg.tiktok.util.JwtUtils;
import com.zkg.tiktok.util.R;
import com.zkg.tiktok.util.UserHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-05-29
 * @Description: 在处理HTTP请求之前执行权限和身份验证
 * @Version: 1.0
 */


@Component
public class AdminInterceptor implements HandlerInterceptor {

    private ObjectMapper objectMapper = new ObjectMapper();

    private UserService userService;

    public AdminInterceptor(UserService userService){
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equals("OPTIONS")){
            return true;
        }

        if (!JwtUtils.checkToken(request)) {
            response(R.error().message("请登录后再操作"),response);
            return false;
        }

        final Long userId = JwtUtils.getUserId(request);
        final User user = userService.getById(userId);
        if (ObjectUtils.isEmpty(user)){
            response(R.error().message("用户不存在"),response);
            return false;
        }
        UserHolder.set(userId);
        return true;
    }

    private boolean response(R r, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(objectMapper.writeValueAsString(r));
        response.getWriter().flush();
        return false;
    }
}
