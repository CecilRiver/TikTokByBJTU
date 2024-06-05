package com.zkg.tiktok.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkg.tiktok.constant.RedisConstant;
import com.zkg.tiktok.entity.user.Favorites;
import com.zkg.tiktok.entity.user.User;
import com.zkg.tiktok.entity.video.Type;
import com.zkg.tiktok.entity.vo.*;
import com.zkg.tiktok.exception.BaseException;
import com.zkg.tiktok.mapper.user.UserMapper;
import com.zkg.tiktok.service.user.FavoritesService;
import com.zkg.tiktok.service.user.FollowService;
import com.zkg.tiktok.service.user.InterestPushService;
import com.zkg.tiktok.service.user.UserService;
import com.zkg.tiktok.util.RedisCacheUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-05-29
 * @Description: 实现类
 * @Version: 1.0
 */


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {



    @Autowired
    private FollowService followService;

    @Autowired
    private RedisCacheUtil redisCacheUtil;
//
//    @Autowired
//    private InterestPushService interestPushService;

    @Autowired
    private FavoritesService favoritesService;

    @Override
    public boolean register(RegisterVO registerVO) throws Exception {

        // 邮箱是否存在
        final int count = count(new LambdaQueryWrapper<User>().eq(User::getEmail, registerVO.getEmail()));
        if (count == 1){
            throw new BaseException("邮箱已被注册");
        }
        final String code = registerVO.getCode();
        final Object o = redisCacheUtil.get(RedisConstant.EMAIL_CODE + registerVO.getEmail());
        if (o == null){
            throw new BaseException("验证码为空");
        }
        if (!code.equals(o)){
            return false;
        }

        final User user = new User();
        user.setNickName(registerVO.getNickName());
        user.setEmail(registerVO.getEmail());
        user.setDescription("这个人很懒...");
        user.setPassword(registerVO.getPassword());
        save(user);

        // 创建默认收藏夹
        final Favorites favorites = new Favorites();
        favorites.setUserId(user.getId());
        favorites.setName("默认收藏夹");
        favoritesService.save(favorites);


        user.setDefaultFavoritesId(favorites.getId());
        updateById(user);
        return true;
    }

    @Override
    public UserVO getInfo(Long userId){

        final User user = getById(userId);
        if (ObjectUtils.isEmpty(user)){
            return new UserVO();
        }
        final UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user,userVO);

        // 查出关注数量
        final long followCount = followService.getFollowCount(userId);

        // 查出粉丝数量
        final long fansCount = followService.getFansCount(userId);
        userVO.setFollow(followCount);
        userVO.setFans(fansCount);
        return userVO;
    }

    @Override
    public Page<User> getFollows(Long userId, BasePage basePage) {
        return null;
    }

    @Override
    public Page<User> getFans(Long userId, BasePage basePage) {
        return null;
    }

    @Override
    public List<User> list(Collection<Long> userIds) {
        return null;
    }

    @Override
    public void subscribe(Set<Long> typeIds) {

    }

    @Override
    public Collection<Type> listSubscribeType(Long userId) {
        return null;
    }

    @Override
    public boolean follows(Long followsUserId) {
        return false;
    }

    @Override
    public void updateUserModel(UserModel userModel) {

    }

    @Override
    public Boolean findPassword(FindPWVO findPWVO) {

        // 从redis中取出
        final Object o = redisCacheUtil.get(RedisConstant.EMAIL_CODE + findPWVO.getEmail());
        if (o==null){
            return false;
        }
        // 校验
        if (Integer.parseInt(o.toString()) != findPWVO.getCode()){
            return false;
        }
        // 修改
        final User user = new User();
        user.setEmail(findPWVO.getEmail());
        user.setPassword(findPWVO.getNewPassword());
        update(user,new UpdateWrapper<User>().lambda().set(User::getPassword,findPWVO.getNewPassword()).eq(User::getEmail,findPWVO.getEmail()));
        return true;
    }

    @Override
    public void updateUser(UpdateUserVO user) {

    }

    @Override
    public Collection<String> searchHistory(Long userId) {
        return null;
    }

    @Override
    public void addSearchHistory(Long userId, String search) {

    }

    @Override
    public void deleteSearchHistory(Long userId) {

    }

    @Override
    public Collection<Type> listNoSubscribeType(Long aLong) {
        return null;
    }

//
//    public void initModel(ModelVO modelVO) {
//        // 初始化模型
//        interestPushService.initUserModel(modelVO.getUserId(),modelVO.getLabels());
//    }



}