package com.lagou.controller;

import com.lagou.domain.Menu;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.RoleMenuVo;
import com.lagou.service.MenuService;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hhc19
 * @date 2022/8/15 0:00
 * @description
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /*
        查询所有角色（条件）
     */
    @RequestMapping("/findAllRole")
    public ResponseResult findAllRole(@RequestBody Role role) {

        List<Role> roles = roleService.findAllRole(role);

        ResponseResult result = new ResponseResult(true, 200, "查询所有角色成功", roles);

        return result;
    }

    @Autowired
    private MenuService menuService;

    /*
        查询所有父子菜单信息（分配菜单的第一个接口）
     */
    @RequestMapping("/findAllMenu")
    public ResponseResult findAllMenuByPid() {

        // -1 表示查询多有的父子级菜单 因为关联的子集菜单会封装
        List<Menu> parentMenuList = menuService.findSubMenuListByPid(-1);

        // 响应数据
        Map<String, Object> map = new HashMap<>();
        map.put("parentMenuList", parentMenuList);

        return new ResponseResult(true, 200, "查询所有父子菜单成功", map);
    }



    /*
        根据角色 ID 查询关联的菜单信息 ID [1, 2, 3, 4]
     */
    @RequestMapping("/findMenuByRoleId")
    public ResponseResult findMenuByRoleId(@RequestParam("roleId") Integer roleId) {

        List<Integer> menu = roleService.findMenuByRoleId(roleId);

        return new ResponseResult(true, 200, "查询角色关联的菜单信息成功", menu);
    }

    /*
        为角色分配菜单
     */
    @RequestMapping("/RoleContextMenu")
    public ResponseResult roleContextMenu(@RequestBody RoleMenuVo roleMenuVo) {

        roleService.roleContextMenu(roleMenuVo);

        return new ResponseResult(true, 200, "响应成功", null);
    }

    /*
        删除角色 先清空与菜单表的关联关系再删除
     */
    @RequestMapping("/deleteRole")
    public ResponseResult deleteRole(@RequestParam("id") Integer id) {

        roleService.deleteRole(id);

        return new ResponseResult(true, 200, "删除角色成功", null);
    }
}
