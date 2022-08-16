package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.UserMapper;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import com.lagou.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author hhc19
 * @date 2022/8/14 3:33
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo<User> findAllUserByPage(UserVo userVo) {

        PageHelper.startPage(userVo.getCurrentPage(), userVo.getPageSize());
        List<User> users = userMapper.findAllUserByPage(userVo);
        PageInfo<User> pageInfo = new PageInfo<>(users);

        System.err.println("总条数："+pageInfo.getTotal());
        System.err.println("总页数："+pageInfo.getPages());
        System.err.println("当前页："+pageInfo.getPageNum());
        System.err.println("每页显示长度："+pageInfo.getPageSize());
        System.err.println("是否第一页："+pageInfo.isIsFirstPage());
        System.err.println("是否最后一页："+pageInfo.isIsLastPage());

        return pageInfo;
    }

    @Override
    public void updateUserStatus(int id, String status) {

        // 封装数据
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        user.setUpdate_time(new Date());

        // 调用 mapper
        userMapper.updateUserStatus(user);
    }

    /*
        用户登录
     */
    @Override
    public User login(User user) throws Exception {

        // 1.调用 mapper 方法 user2:包含了密文密码
        User user2 = userMapper.login(user);

        if (null != user2 && Md5.verify(user.getPassword(), "lagou", user2.getPassword())) {
            return user2;
        } else {
            return null;
        }
    }

    /*
        分配角色（回显）
     */
    @Override
    public List<Role> findUserRelationRoleById(Integer id) {
        return userMapper.findUserRelationRoleById(id);
    }

    @Override
    public void userContextRole(UserVo userVo) {

        // 1.根据用户 id 清空中间表关联关系
        userMapper.deleteUserContextRole(userVo.getUserId());

        // 2.再重新建立关联关系
        for (Integer roleId : userVo.getRoleIdList()) {

            // 封装数据
            User_Role_relation user_role_relation = new User_Role_relation();
            user_role_relation.setRoleId(roleId);
            user_role_relation.setUserId(userVo.getUserId());

            Date date = new Date();
            user_role_relation.setCreatedTime(date);
            user_role_relation.setUpdatedTime(date);

            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedby("system");

            userMapper.userContextRole(user_role_relation);
        }
    }

    /*
        获取用户权限信息
     */
    @Override
    public ResponseResult getUserPermissions(Integer userId) {

        // 1.根据当前用户id查询该用户所拥有的角色
        List<Role> roleList = userMapper.findUserRelationRoleById(userId);

        // 2.获取角色 id，保存到 list 集合中
        List<Integer> roleIds = new ArrayList<Integer>();
        for (Role role : roleList) {
            roleIds.add(role.getId());
        }

        // 3.根据角色 id 查询父菜单
        List<Menu> parentMenuList = userMapper.findParentMenuByRoleId(roleIds);

        // 4.再对父菜单关联的子菜单进行关联查询封装
        for (Menu menu : parentMenuList) {
            List<Menu> subMenuList = userMapper.findSubMenuByPid(menu.getId());
            menu.setSubMenuList(subMenuList);
        }

        // 5.获取资源信息
        List<Resource> resourceList = userMapper.findResourceByRoleId(roleIds);

        // 6.封装数据并进行返回
        Map<String, Object> map = new HashMap<>();
        map.put("menuList", parentMenuList);
        map.put("resourceList", resourceList);

        return new ResponseResult(true, 1, "获取用户权限信息成功", map);
    }

}
