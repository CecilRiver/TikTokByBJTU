package com.zkg.tiktok.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zkg.tiktok.entity.video.Type;
import com.zkg.tiktok.entity.video.Video;
import com.zkg.tiktok.entity.video.VideoShare;
import com.zkg.tiktok.entity.vo.BasePage;
import com.zkg.tiktok.service.user.UserService;
import com.zkg.tiktok.service.video.TypeService;
import com.zkg.tiktok.service.video.VideoService;
import com.zkg.tiktok.util.JwtUtils;
import com.zkg.tiktok.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-06-11
 * @Description:
 * @Version: 1.0
 */

@Api(tags = "")
@RestController
@RequestMapping("/tiktok/index")
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private TypeService typeService;

    /**
     * 兴趣推送视频
     * @return
     */
    @ApiOperation("兴趣推送视频")
    @GetMapping("/pushVideos")
    public R pushVideos(HttpServletRequest request){
        final Long userId = JwtUtils.getUserId(request);
        return R.ok().data(videoService.pushVideos(userId));
    }

    /**
     * 搜索视频
     * @return
     */
    @ApiOperation("搜索视频")
    @GetMapping("/search")
    public R searchVideo(@RequestParam(required = false) String searchName, BasePage basePage, HttpServletRequest request){
        return R.ok().data(videoService.searchVideo(searchName, basePage, JwtUtils.getUserId(request)));
    }

    /**
     * 根据视频分类获取
     * @param typeId
     * @return
     */
    @ApiOperation("根据视频分类获取视频")
    @GetMapping("/video/type/{typeId}")
    public R getVideoByTypeId(@PathVariable Long typeId){
        System.out.println("根据视频分类获取视频:"+typeId);
        return R.ok().data(videoService.getVideoByTypeId(typeId));
    }

    /**
     * 获取所有分类
     * @return
     */
    @ApiOperation("获取所有视频分类")
    @GetMapping("/types")
    public R getTypes(HttpServletRequest request){
        Long userId = JwtUtils.getUserId(request);
        if (userId == null) {
            System.out.println("USERID null");
        }
        System.out.println(userId);
        final List<Type> types = typeService.list(new LambdaQueryWrapper<Type>().select(Type::getIcon, Type::getId, Type::getName).orderByDesc(Type::getSort));
        final Set<Long> set = userService.listSubscribeType(JwtUtils.getUserId(request)).stream().map(Type::getId).collect(Collectors.toSet());

        if (types == null) {
            throw new NullPointerException("Subscribed Types are null");
        }
        for (Type type : types) {
            type.setUsed(set.contains(type.getId()));
        }
        return R.ok().data(types);
    }

    /**
     * 分享视频
     * @param videoId
     * @param request
     * @return
     */
    @ApiOperation("分享视频")
    @PostMapping("/share/{videoId}")
    public R share(@PathVariable Long videoId, HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for") == null ? request.getRemoteAddr() : request.getHeader("x-forwarded-for");
        final VideoShare videoShare = new VideoShare();
        videoShare.setVideoId(videoId);
        videoShare.setIp(ip);
        if (JwtUtils.checkToken(request)) {
            videoShare.setUserId(JwtUtils.getUserId(request));
        }
        videoService.shareVideo(videoShare);
        return R.ok();
    }

    /**
     * 根据id获取视频详情
     * @param id
     * @return
     */
    @ApiOperation("根据ID获取视频详情")
    @GetMapping("/video/{id}")
    public R getVideoById(@PathVariable Long id, HttpServletRequest request){
        final Long userId = JwtUtils.getUserId(request);
        return R.ok().data(videoService.getVideoById(id, userId));
    }

    /**
     * 获取热度排行榜
     * @return
     */
    @ApiOperation("获取视频热度排行榜")
    @GetMapping("/video/hot/rank")
    public R listHotRank(){
        return R.ok().data(videoService.hotRank());
    }

    /**
     * 根据视频标签推送相似视频
     * @param video
     * @return
     */
    @ApiOperation("根据视频标签推送相似视频")
    @GetMapping("/video/similar")
    public R pushVideoSimilar(Video video){
        return R.ok().data(videoService.listSimilarVideo(video));
    }

    /**
     * 推送热门视频
     * @return
     */
    @ApiOperation("推送热门视频")
    @GetMapping("/video/hot")
    public R listHotVideo(){
        return R.ok().data(videoService.listHotVideo());
    }

    /**
     * 根据用户id获取视频
     * @param userId
     * @param basePage
     * @return
     */
    @ApiOperation("根据用户ID获取视频")
    @GetMapping("/video/user")
    public R listVideoByUserId(@RequestParam(required = false) Long userId, BasePage basePage, HttpServletRequest request){
        userId = userId == null ? JwtUtils.getUserId(request) : userId;
        return R.ok().data(videoService.listByUserIdOpenVideo(userId, basePage));
    }

    /**
     * 获取用户搜索记录
     * @return
     */
    @ApiOperation("获取用户搜索记录")
    @GetMapping("/search/history")
    public R searchHistory(HttpServletRequest request){
        return R.ok().data(userService.searchHistory(JwtUtils.getUserId(request)));
    }

    /**
     * 删除搜索记录
     * @return
     */
    @ApiOperation("删除用户搜索记录")
    @DeleteMapping("/search/history")
    public R deleteSearchHistory(HttpServletRequest request){
        userService.deleteSearchHistory(JwtUtils.getUserId(request));
        return R.ok();
    }
}
