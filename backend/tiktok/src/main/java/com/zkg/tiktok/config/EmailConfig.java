package com.zkg.tiktok.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-05-29
 * @Description: 邮件相关
 * @Version: 1.0
 */


@Configuration
public class EmailConfig {

    @Bean
    public SimpleMailMessage simpleMailMessage(){
        return new SimpleMailMessage();
    }


}
