package com.zkg.tiktok.service.user;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-06-04
 * @Description: 邮件
 * @Version: 1.0
 */
public interface EmailService {
    void send(String email,String context);
}
