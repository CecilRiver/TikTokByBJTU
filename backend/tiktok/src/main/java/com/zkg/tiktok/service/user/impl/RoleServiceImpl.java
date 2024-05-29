package com.zkg.tiktok.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zkg.tiktok.entity.user.Role;
import com.zkg.tiktok.entity.user.RolePermission;
import com.zkg.tiktok.entity.user.Tree;
import com.zkg.tiktok.entity.user.UserRole;
import com.zkg.tiktok.mapper.RoleMapper;
import com.zkg.tiktok.service.user.RolePermissionService;
import com.zkg.tiktok.service.user.RoleService;
import com.zkg.tiktok.service.user.UserRoleService;
import com.zkg.tiktok.util.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import com.zkg.tiktok.entity.vo.*;
/**
 * @Author: 张凯歌
 * @CreateTime: 2024-05-29
 * @Description: 实现类
 * @Version: 1.0
 */


@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


//    @Resource
//    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserRoleService userRoleService;



//    @Override
//    public List<Tree> tree() {
//        List<Tree> trees = permissionService.list(null).stream().map(permission -> {
//
//            Tree tree = new Tree();
//            BeanUtils.copyProperties(permission, tree);
//            tree.setTitle(permission.getName());
//            tree.setSpread(true);
//            return tree;
//        }).collect(Collectors.toList());
//
//
//        // 找到根节点
//        List<Tree> parent = trees.stream().filter(tree -> tree.getPId().compareTo(0L) == 0).collect(Collectors.toList());
//        for (Tree item : parent) {
//            item.setChildren(new ArrayList<Tree>());
//            item.getChildren().add(findChildren(item,trees));
//        }
//
//        return parent;
//    }



    @Override
    @Transactional
    public R removeRole(String id) {
        try{
            // 删除角色权限中间表
            rolePermissionService.remove(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId,id));
            // 删除角色表
            baseMapper.deleteById(id);
            // 删除用户角色表
            userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleId,id));
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error().message("删除失败");
        }
        return R.ok().message("删除成功");
    }

    @Override
    @Transactional
    public R gavePermission(AuthorityVO authorityVO) {
        try{
            rolePermissionService.remove(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId,authorityVO.getRid()));
            List<RolePermission> list = new ArrayList<>();
            Integer rid = authorityVO.getRid();
            for (Integer pId : authorityVO.getPid()) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(rid);
                rolePermission.setPermissionId(pId);
                list.add(rolePermission);
            }
            rolePermissionService.saveBatch(list);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error().message("分配权限失败");
        }
        return R.ok().message("分配权限成功");
    }

    @Override
    @Transactional
    public R gaveRole(AssignRoleVO assignRoleVO) {
        // 获取被分配角色的用户信息
        Long uId = assignRoleVO.getUId();
        try{
            userRoleService.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,uId));
            List<UserRole> userRoles = new ArrayList<>();
            for (Long id : assignRoleVO.getRId()) {
                UserRole userRole = new UserRole();
                userRole.setUserId(uId);
                userRole.setRoleId(id);
                userRoles.add(userRole);
            }
            userRoleService.saveBatch(userRoles);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return R.error().message("分配角色失败");
        }
        return R.ok().message("分配角色成功");
    }

    private Tree findChildren(Tree datum, List<Tree> trees) {
        datum.setChildren(new ArrayList<Tree>());
        for (Tree tree : trees) {
            if (tree.getPId().compareTo(datum.getId()) == 0) {
                datum.getChildren().add(findChildren(tree,trees));
            }
        }
        return datum;
    }
}
