package com.zkg.tiktok.service.user.impl;

import com.zkg.tiktok.service.user.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-06-04
 * @Description: 邮件业务层实现
 * @Version: 1.0
 */


@Service
public class EmailServiceImpl implements EmailService {


    @Autowired
    private SimpleMailMessage simpleMailMessage;

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    String fromName;



    @Override
    @Async
    public void send(String email, String context) {
        simpleMailMessage.setSubject("北交tiktok");
        simpleMailMessage.setFrom(fromName);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setText(context);
        javaMailSender.send(simpleMailMessage);
    }
}
