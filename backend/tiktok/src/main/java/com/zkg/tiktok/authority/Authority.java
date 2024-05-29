package com.zkg.tiktok.authority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-05-29
 * @Description: 用于授权的注解
 * @Version: 1.0
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Authority {

    /**权限标识*/
    String[] value();

    /**具体执行校验类*/
    Class verify() default DefaultAuthorityVerify.class;

}
