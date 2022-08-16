package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVo;

import java.util.List;

/**
 * @author hhc19
 * @date 2022/8/14 3:33
 * @description
 */
public interface UserService {

    /*
        用户分页&多条件组合查询
     */
    public PageInfo<User> findAllUserByPage(UserVo userVo);

    /*
        用户状态设置
     */
    public void updateUserStatus(int id, String status);

    /*
        用户登录
     */
    public User login(User user) throws Exception;

    /*
        分配角色（回显）
     */
    public List<Role> findUserRelationRoleById(Integer id);

    /*
        用户关联角色
     */
    public void userContextRole(UserVo userVo);


    /*
        获取用户权限，进行菜单动态展示的方法
     */
    public ResponseResult getUserPermissions(Integer userId);
}
