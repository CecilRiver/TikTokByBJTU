package com.zkg.tiktok.authority;

import com.zkg.tiktok.util.JwtUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-05-29
 * @Description: 权限验证
 * @Version: 1.0
 */


@Component(value = "postMappingAuthorityVerify")
public class UnifyAuthorityVerify extends DefaultAuthorityVerify{
    @Override
    public Boolean authorityVerify(HttpServletRequest request, String... permissions) {

        Long uId = JwtUtils.getUserId(request);
        for (String permission : permissions) {
            if (!AuthorityUtils.verify(uId,permission)) {
                return false;
            }
        }
        return true;
    }
}