package com.zkg.tiktok.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zkg.tiktok.entity.user.Role;
import com.zkg.tiktok.entity.user.Tree;
import com.zkg.tiktok.entity.vo.AssignRoleVO;
import com.zkg.tiktok.entity.vo.AuthorityVO;
import com.zkg.tiktok.util.R;

import java.util.List;

/**
 * @Author: 张凯歌
 * @CreateTime: 2024-05-29
 * @Description: 业务层接口
 * @Version: 1.0
 */
public interface RoleService extends IService<Role> {

    List<Tree> tree();

    R removeRole(String id);

    R gavePermission(AuthorityVO authorityVO);

    R gaveRole(AssignRoleVO assignRoleVO);

}
