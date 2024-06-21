package com.zkg.tiktok.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-05-29
 * @Description: 限流配置
 * @Version: 1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {

    int limit() default 0;

    long time() default 0;

    String key() default "";

    String msg() default "系统服务繁忙";
}
