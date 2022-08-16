package com.lagou.service.impl;

import com.lagou.dao.MenuMapper;
import com.lagou.domain.Menu;
import com.lagou.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author hhc19
 * @date 2022/8/15 12:58
 * @description
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> findSubMenuListByPid(int pid) {
        return menuMapper.findSubMenuListByPid(pid);
    }

    @Override
    public List<Menu> findAllMenu() {
        return menuMapper.findAllMenu();
    }

    @Override
    public Menu findMenuById(Integer id) {
        return menuMapper.findMenuById(id);
    }

    @Override
    public void saveMenu(Menu menu) {

        // 封装数据
       /* if (menu.getParentId() == -1) {
            menu.setLevel(0);
        } else {
            menu.setLevel(1);
        }

        Date date = new Date();
        menu.setCreatedTime(date);
        menu.setUpdatedTime(date);

        menu.setCreatedBy("system");
        menu.setUpdatedBy("system");*/

        // 调用 mapper
        menuMapper.saveMenu(menu);
    }

    @Override
    public void updateMenu(Menu menu) {
        // 封装数据
        /*if (menu.getParentId() == -1) {
            menu.setLevel(0);
        } else {
            menu.setLevel(1);
        }

        Date date = new Date();
        menu.setUpdatedTime(date);

        menu.setUpdatedBy("system");*/

        // 调用 mapper
        menuMapper.updateMenu(menu);
    }
}
