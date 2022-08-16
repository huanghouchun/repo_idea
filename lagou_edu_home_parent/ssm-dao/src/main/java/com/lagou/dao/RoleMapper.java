package com.lagou.dao;

import com.lagou.domain.Role;
import com.lagou.domain.Role_menu_relation;

import java.util.List;

/**
 * @author hhc19
 * @date 2022/8/14 23:50
 * @description
 */
public interface RoleMapper {

    /*
        查询所有角色&条件进行查询
        可以直接使用 String name 来接收前台传递的参数 此处参数是 Role 是为了保证扩展性
    */
    public List<Role> findAllRole(Role role);

    /*
        根据角色id查询该角色关联的菜单信息 id [1, 2, 3, 5]
     */
    public List<Integer> findMenuByRoleId(Integer roleId);

    /*
        根据 roleId 清空中间表的关联关系
     */
    public void deleteRoleContextMenu(Integer roleId);

    /*
        为角色分配菜单信息
     */
    public void roleContextMenu(Role_menu_relation role_menu_relation);

    /*
        删除角色
     */
    public void deleteRole(Integer roleId);
}
