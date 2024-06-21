package com.zkg.tiktok.util;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-05-29
 * @Description: 用线程存储变量
 * @Version: 1.0
 */


public class UserHolder {



    private static ThreadLocal<Long> userThreadLocal = new ThreadLocal<>();
    // 添加
    public static void set(Object id){
        userThreadLocal.set(Long.valueOf(id.toString()));
    }
    // 获取
    public static Long get(){
        return userThreadLocal.get();
    }
    // 删除
    public static void clear(){
        userThreadLocal.remove();
    }
}
