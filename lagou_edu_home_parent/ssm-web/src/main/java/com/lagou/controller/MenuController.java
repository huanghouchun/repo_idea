package com.lagou.controller;

import com.lagou.domain.Menu;
import com.lagou.domain.ResponseResult;
import com.lagou.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hhc19
 * @date 2022/8/15 12:59
 * @description
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /*
        查询所有菜单信息
     */
    @RequestMapping("/findAllMenu")
    public ResponseResult findAllMenu() {

        List<Menu> allMenu = menuService.findAllMenu();

        return new ResponseResult(true, 200, "查询所有菜单信息成功", allMenu);
    }

    /*
        回显菜单信息
     */
    @RequestMapping("/findMenuInfoById")
    public ResponseResult findMenuInfoById(Integer id) {

        // 如果是新增菜单,则id值为 -1, 修改菜单 则为当前选择的id值
        // 根据 id 的值判断当前是更新还是添加操作 判断 id 的值是否为 -1
        if (id == -1) {
            // 添加 回显信息中不需要查询 menu 信息
            List<Menu> subMenuListByPid = menuService.findSubMenuListByPid(-1);

            // 封装数据
            Map<String, Object> map = new HashMap<>();
            map.put("menuInfo", null);
            map.put("parentMenuList", subMenuListByPid);

            return new ResponseResult(true, 200, "添加回显成功", map);
        } else {

            // 修改操作 回显所有 menu 信息
            Menu menu = menuService.findMenuById(id);
            List<Menu> subMenuListByPid = menuService.findSubMenuListByPid(-1);

            // 封装数据
            Map<String, Object> map = new HashMap<>();
            map.put("menuInfo", menu);
            map.put("parentMenuList", subMenuListByPid);

            return new ResponseResult(true, 200, "修改回显成功", map);
        }

    }


    /*
        新增&修改菜单
     */
    @RequestMapping("/saveOrUpdateMenu")
    public ResponseResult saveOrUpdateMenu(@RequestBody Menu menu) {

        if (null != menu.getId()) {

            // 修改操作
            menuService.updateMenu(menu);
            return new ResponseResult(true, 200, "修改菜单信息成功", null);
        } else {

            // 新增操作
            menuService.saveMenu(menu);
            return new ResponseResult(true, 200, "新增菜单信息成功", null);
        }
    }
}
