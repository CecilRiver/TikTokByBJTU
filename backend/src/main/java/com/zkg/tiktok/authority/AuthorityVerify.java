package com.zkg.tiktok.authority;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-05-29
 * @Description: 定义权限接口
 * @Version: 1.0
 */
public interface AuthorityVerify {
    Boolean authorityVerify(HttpServletRequest request, String[] permissions);

}
