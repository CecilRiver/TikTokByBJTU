package com.zkg.tiktok.authority;

import com.zkg.tiktok.util.JwtUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-05-29
 * @Description: 使用JWT校验用户权限
 * @Version: 1.0
 */


public class BaseAuthority implements AuthorityVerify{

    @Override
    public Boolean authorityVerify(HttpServletRequest request, String[] permissions) {
        if (!JwtUtils.checkToken(request)) {
            return false;
        }
        // 获取当前用户权限
        Long uId = JwtUtils.getUserId(request);
        for (String permission : permissions) {
            if (!AuthorityUtils.verify(uId,permission)) {
                return false;
            }
        }

        return true;
    }
}
