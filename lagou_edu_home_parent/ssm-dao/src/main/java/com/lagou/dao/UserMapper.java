package com.lagou.dao;

import com.lagou.domain.*;

import java.util.List;

/**
 * @author hhc19
 * @date 2022/8/14 3:12
 * @description
 */
public interface UserMapper {

    /*
        用户分页&多条件组合查询
     */
    public List<User> findAllUserByPage(UserVo userVo);

    /*
        用户状态设置
     */
    public void updateUserStatus(User user);

    /*
        用户登录（根据用户名查询具体的用户信息）
     */
    public User login(User user);

    /*
        根据用户 id 清空中间表
     */
    public void deleteUserContextRole(Integer userId);

    /*
        分配角色
     */
    public void userContextRole(User_Role_relation user_role_relation);

    /*
        1.根据用户 id 查询关联的角色信息 多个角色
     */
    public List<Role> findUserRelationRoleById(Integer id);

    /*
        2.根据角色 id 查询角色所拥有的 顶级菜单(parent_id = -1)
     */
    public List<Menu> findParentMenuByRoleId(List<Integer> ids);

    /*
        3.根据 PID 查询子级菜单信息
     */
    public List<Menu> findSubMenuByPid(Integer pid);

    /*
        4.获取用户拥有的资源权限信息
     */
    public List<Resource> findResourceByRoleId(List<Integer> ids);
    
    public List<Resource> findResourceByRoleId2(List<Integer> ids);
}
