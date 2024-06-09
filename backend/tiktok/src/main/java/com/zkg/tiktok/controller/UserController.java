package com.zkg.tiktok.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zkg.tiktok.config.QiNiuConfig;
import com.zkg.tiktok.entity.user.Favorites;
import com.zkg.tiktok.entity.vo.BasePage;
import com.zkg.tiktok.entity.vo.Model;
import com.zkg.tiktok.entity.vo.UpdateUserVO;
import com.zkg.tiktok.entity.vo.UserModel;
import com.zkg.tiktok.service.user.FavoritesService;
import com.zkg.tiktok.service.user.UserService;
import com.zkg.tiktok.util.R;
import com.zkg.tiktok.util.UserHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-06-09
 * @Description: 用户信息相关
 * @Version: 1.0
 */

@Api(tags = "用户信息相关接口")
@RestController
@RequestMapping("/tiktok/customer")
public class UserController {

    @Autowired
    QiNiuConfig qiNiuConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private FavoritesService favoritesService;


    /**
     * 获取个人信息
     * @param userId
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取个人信息", notes = "根据用户ID获取用户的详细信息")
    @GetMapping("/getInfo/{userId}")
    public R getInfo(@PathVariable Long userId){
        return R.ok().data(userService.getInfo(userId));
    }

    /**
     * 获取用户信息
     * @return
     */
    @ApiOperation(value = "获取当前登录用户的信息", notes = "获取当前登录用户的详细信息")
    @GetMapping("/getInfo")
    public R getDefaultInfo(){
        return R.ok().data(userService.getInfo(UserHolder.get()));
    }

    /**
     * 获取关注人员
     * @param basePage
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取关注人员", notes = "根据用户ID获取用户关注的人员列表")
    @GetMapping("/follows")
    public R getFollows(BasePage basePage, Long userId){
        return R.ok().data(userService.getFollows(userId, basePage));
    }

    /**
     * 获取粉丝
     * @param basePage
     * @param userId
     * @return
     */
    @ApiOperation(value = "获取粉丝", notes = "根据用户ID获取用户的粉丝列表")
    @GetMapping("/fans")
    public R getFans(BasePage basePage, Long userId){
        return R.ok().data(userService.getFans(userId, basePage));
    }


    /**
     * 获取所有的收藏夹
     * @return
     */
    @ApiOperation(value = "获取所有的收藏夹", notes = "获取当前用户的所有收藏夹列表")
    @GetMapping("/favorites")
    public R listFavorites(){
        final Long userId = UserHolder.get();
        List<Favorites> favorites = favoritesService.listByUserId(userId);
        return R.ok().data(favorites);
    }


    /**
     * 获取指定收藏夹
     * @param id
     * @return
     */
    @ApiOperation(value = "获取指定收藏夹", notes = "根据收藏夹ID获取收藏夹的详细信息")
    @GetMapping("/favorites/{id}")
    public R getFavorites(@PathVariable Long id){
        return R.ok().data(favoritesService.getById(id));
    }

    /**
     * 添加/修改收藏夹
     * @param favorites
     * @return
     */
    @ApiOperation(value = "添加/修改收藏夹", notes = "添加或修改用户的收藏夹")
    @PostMapping("/favorites")
    public R saveOrUpdateFavorites(@RequestBody @Validated Favorites favorites){
        final Long userId = UserHolder.get();
        final Long id = favorites.getId();
        favorites.setUserId(userId);
        final int count = favoritesService.count(new LambdaQueryWrapper<Favorites>()
                .eq(Favorites::getName, favorites.getName()).eq(Favorites::getUserId, userId).ne(Favorites::getId,favorites.getId()));
        if (count == 1){
            return R.error().message("已存在相同名称的收藏夹");
        }
        favoritesService.saveOrUpdate(favorites);
        return R.ok().message(id != null ? "修改成功" : "添加成功");
    }

    /**
     * 删除收藏夹
     * @param id
     * @return
     */
    @ApiOperation(value = "删除收藏夹", notes = "根据收藏夹ID删除收藏夹")
    @DeleteMapping("/favorites/{id}")
    public R deleteFavorites(@PathVariable Long id){
        favoritesService.remove(id, UserHolder.get());
        return R.ok().message("删除成功");
    }


    /**
     * 订阅分类
     */
    @ApiOperation(value = "订阅分类", notes = "订阅或取消订阅分类")
    @PostMapping("/subscribe")
    public R subscribe(@RequestParam(required = false) String types){
        final HashSet<Long> typeSet = new HashSet<>();
        String msg = "取消订阅";
        if (!ObjectUtils.isEmpty(types)){
            for (String s : types.split(",")) {
                typeSet.add(Long.parseLong(s));
            }
            msg = "订阅成功";
        }
        userService.subscribe(typeSet);
        return R.ok().message(msg);
    }

    /**
     * 获取用户订阅的分类
     * @return
     */
    @ApiOperation(value = "获取用户订阅的分类", notes = "获取当前用户已订阅的分类列表")
    @GetMapping("/subscribe")
    public R listSubscribeType(){
        return R.ok().data(userService.listSubscribeType(UserHolder.get()));
    }

    /**
     * 获取用户没订阅的分类
     * @return
     */
    @ApiOperation(value = "获取用户未订阅的分类", notes = "获取当前用户未订阅的分类列表")
    @GetMapping("/noSubscribe")
    public R listNoSubscribeType(){
        return R.ok().data(userService.listNoSubscribeType(UserHolder.get()));
    }

    /**
     * 关注/取关
     * @param followsUserId
     * @return
     */
    @ApiOperation(value = "关注/取消关注", notes = "根据用户ID进行关注或取消关注操作")
    @PostMapping("/follows")
    public R follows(@RequestParam Long followsUserId){
        return R.ok().message(userService.follows(followsUserId) ? "已关注" : "已取关");
    }

    /**
     * 用户停留时长修改模型
     * @param model
     * @return
     */
    @ApiOperation(value = "修改用户停留时长模型", notes = "根据模型修改用户停留时长，仅当score为-0.5或1.0时更新模型")
    @PostMapping("/updateUserModel")
    public R updateUserModel(@RequestBody Model model){
        final Double score = model.getScore();
        if (score == -0.5 || score == 1.0){
            final UserModel userModel = new UserModel();
            userModel.setUserId(UserHolder.get());
            userModel.setModels(Collections.singletonList(model));
            userService.updateUserModel(userModel);
        }
        return R.ok();
    }

    /**
     * 获取用户上传头像的token
     * @return
     */
    @ApiOperation(value = "获取用户上传头像的token", notes = "获取用户上传头像所需的七牛云token")
    @GetMapping("/avatar/token")
    public R avatarToken(){
        return R.ok().data(qiNiuConfig.imageUploadToken());
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @ApiOperation(value = "修改用户信息", notes = "修改当前登录用户的信息")
    @PutMapping
    public R updateUser(@RequestBody @Validated UpdateUserVO user){
        userService.updateUser(user);
        return R.ok().message("修改成功");
    }

}
