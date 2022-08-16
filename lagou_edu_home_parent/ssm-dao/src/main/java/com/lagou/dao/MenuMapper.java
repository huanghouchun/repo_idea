package com.lagou.dao;

import com.lagou.domain.Menu;

import java.util.List;

/**
 * @author hhc19
 * @date 2022/8/15 12:49
 * @description
 */
public interface MenuMapper {

    /*
        查询所有父子菜单信息

        Pid parent_id
     */
    public List<Menu> findSubMenuListByPid(int pid);

    /*
        查询所有菜单列表
     */
    public List<Menu> findAllMenu();

    /*
        根据 id 查询具体菜单信息
     */
    public Menu findMenuById(Integer id);

    /*
        新增菜单
     */
    public void saveMenu(Menu menu);

    /*
        修改菜单
     */
    public void updateMenu(Menu menu);
}
