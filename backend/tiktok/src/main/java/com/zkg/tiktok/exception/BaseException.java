package com.zkg.tiktok.exception;

import lombok.Data;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-06-04
 * @Description: 异常
 * @Version: 1.0
 */


@Data
public class BaseException extends RuntimeException {

    String msg;

    public BaseException(String msg){
        this.msg = msg;
    }

}
