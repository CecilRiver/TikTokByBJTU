package com.zkg.tiktok.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-05-29
 * @Description: 相关配置
 * @Version: 1.0
 */


public class PageConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        final PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }
}
