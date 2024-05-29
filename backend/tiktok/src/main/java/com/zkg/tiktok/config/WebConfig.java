package com.zkg.tiktok.config;

import com.zkg.tiktok.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-05-29
 * @Description: 网络配置
 * @Version: 1.0
 */


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private UserService userService;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor(userService))
                .addPathPatterns("/admin/**","/authorize/**")
                .addPathPatterns("/luckyjourney/**")
                .excludePathPatterns("/luckyjourney/login/**","/luckyjourney/index/**","/luckyjourney/cdn/**", "/luckyjourney/file/**");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        String[] url = {"http://101.35.228.84:8882","http://101.35.228.84:5378","http://127.0.0.1:5378","http://localhost:5378"};

        registry.addMapping("/**")
                .allowedOrigins(url)
                .allowCredentials(true)
                .allowedMethods("*")   // 允许跨域的方法，可以单独配置
                .allowedHeaders("*");  // 允许跨域的请求头，可以单独配置;
    }
}
