package com.zkg.tiktok.controller;

import com.zkg.tiktok.config.LocalCache;
import com.zkg.tiktok.config.QiNiuConfig;
import com.zkg.tiktok.entity.File;
import com.zkg.tiktok.entity.Setting;
import com.zkg.tiktok.service.FileService;
import com.zkg.tiktok.service.SettingService;
import com.zkg.tiktok.util.R;
import com.zkg.tiktok.util.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-06-09
 * @Description: 文件上传与访问
 * @Version: 1.0
 */

@Api(tags = "文件上传与访问接口")
@RestController
@RequestMapping("/tiktok/file")
public class FileController implements InitializingBean {

    @Autowired
    FileService fileService;

    @Autowired
    QiNiuConfig qiNiuConfig;

    @Autowired
    SettingService settingService;

    /**
     * 保存到文件表
     * @param fileKey 文件唯一标识符
     * @return
     */
    @ApiOperation(value = "保存文件", notes = "将文件信息保存到文件表中")
    @PostMapping
    public R save(@RequestParam String fileKey){
        return R.ok().data(fileService.save(fileKey, UserHolder.get()));
    }

    /**
     * 获取上传文件的Token
     * @param type 文件类型
     * @return
     */
    @ApiOperation(value = "获取上传Token", notes = "获取上传文件所需的Token")
    @GetMapping("/getToken")
    public R token(@RequestParam String type){
        return R.ok().data(qiNiuConfig.uploadToken(type));
    }

    /**
     * 获取文件的信任URL并重定向
     * @param request Http请求对象
     * @param response Http响应对象
     * @param fileId 文件ID
     * @throws IOException
     */
    @ApiOperation(value = "获取文件URL", notes = "根据文件ID获取文件的信任URL并重定向到该URL")
    @GetMapping("/{fileId}")
    public void getUUid(HttpServletRequest request, HttpServletResponse response, @PathVariable Long fileId) throws IOException {
        // 如果不是指定ip调用的该接口，则不返回
        File url = fileService.getFileTrustUrl(fileId);
        System.out.println("视频url地址："+url);
        response.setContentType(url.getType());
        response.sendRedirect(url.getFileKey());
    }

    /**
     * 授权文件访问
     * @param uuid 授权UUID
     * @param response Http响应对象
     * @throws IOException
     */
    @ApiOperation(value = "授权文件访问", notes = "授权文件访问，如果授权成功则返回200状态码")
    @PostMapping("/auth")
    public void auth(@RequestParam(required = false) String uuid, HttpServletResponse response) throws IOException {
        if (uuid == null || LocalCache.containsKey(uuid) == null){
            response.sendError(401);
        }else {
            LocalCache.rem(uuid);
            response.sendError(200);
        }
    }

    /**
     * 初始化允许访问的IP地址列表
     * @throws Exception
     */
    @Override
    @ApiOperation(value = "初始化允许访问的IP地址列表", notes = "在应用启动时初始化允许访问的IP地址列表")
    public void afterPropertiesSet() throws Exception {
        final Setting setting = settingService.list(null).get(0);
        for (String s : setting.getAllowIp().split(",")) {
            LocalCache.put(s,true);
        }
    }
}
