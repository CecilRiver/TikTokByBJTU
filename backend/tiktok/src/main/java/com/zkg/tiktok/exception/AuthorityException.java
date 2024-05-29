package com.zkg.tiktok.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-05-29
 * @Description: 异常处理
 * @Version: 1.0
 */


@Data
@AllArgsConstructor
public class AuthorityException extends Exception{

    private int code;

    private String msg;

    public AuthorityException(String msg){
        super(msg);
    }
}
