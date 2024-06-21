package com.zkg.tiktok.authority;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-05-29
 * @Description: 默认权限相关
 * @Version: 1.0
 */


public class DefaultAuthorityVerify implements AuthorityVerify{


    @Override
    public Boolean authorityVerify(HttpServletRequest request, String[] permissions) {
        return true;
    }
}
