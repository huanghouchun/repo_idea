package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.ResponseResult;
import com.lagou.domain.Role;
import com.lagou.domain.User;
import com.lagou.domain.UserVo;
import com.lagou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author hhc19
 * @date 2022/8/14 3:37
 * @description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /*
        用户分页&多条件组合查询
     */
    @RequestMapping("/findAllUserByPage")
    public ResponseResult findAllUserByPage(@RequestBody UserVo userVo) {

        PageInfo<User> pageInfo = userService.findAllUserByPage(userVo);

        return new ResponseResult(true, 200, "用户分页&多条件组合查询成功", pageInfo);
    }

    /*
        用户状态设置

        ENABLE能登录，DISABLE不能登录
     */
    @RequestMapping("/updateUserStatus")
    public ResponseResult updateUserStatus(@RequestParam("id") int id, @RequestParam("status") String status) {

        if("ENABLE".equalsIgnoreCase(status)){
            status = "DISABLE";
        }else{
            status = "ENABLE";
        }

        userService.updateUserStatus(id, status);

        return new ResponseResult(true, 200, "用户状态设置成功", status);
    }

    /*
        用户登录
     */
    @RequestMapping("/login")
    public ResponseResult login(User user, HttpServletRequest request) throws Exception {

        User user1 = userService.login(user);

        if (null != user1) {

            // 保存用户id 及 access_token 到session中
            HttpSession session = request.getSession();

            // access_token 可以随机生成一个只要不重复即可
            // access_token 就是在下一次请求发来时保证是同一个用户且在登录状态
            String access_token = UUID.randomUUID().toString();
            session.setAttribute("access_token", access_token);
            session.setAttribute("user_id", user1.getId());

            // 将查出来的信息响应给前台
            Map<String, Object> map = new HashMap<>();
            map.put("access_token", access_token);
            map.put("user_id", user1.getId());

            // 此处还需要将 查询出来的 user1 对象存放在 map 中，因为在前台进行用户登出功能时需要用到该 user1 的其他信息
            // 将查询出来的 user1,也存到 map 中再去响应给前台
            map.put("user", user1);

            return new ResponseResult(true, 1, "登录成功", map);
        } else {

            return new ResponseResult(true, 400, "用户名密码错误", null);
        }
    }

    /*
        分配角色（回显）
     */
    @RequestMapping("findUserRoleById")
    public ResponseResult findUserRelationRoleById(Integer id) {

        List<Role> roleList = userService.findUserRelationRoleById(id);

        return new ResponseResult(true, 200, "分配角色回显成功", roleList);
    }

    /*
        分配角色
     */
    @RequestMapping("/userContextRole")
    public ResponseResult userContextRole(@RequestBody UserVo userVo) {

        userService.userContextRole(userVo);

        return new ResponseResult(true, 200, "分配角色成功", null);
    }

    /*
        获取用户权限 进行菜单动态展示

        测试该方法首先得执行一下 login 方法，要先完成用户登录才能获取到用户所拥有的权限信息
     */
    @RequestMapping("/getUserPermissions")
    public ResponseResult getUserPermissions(HttpServletRequest request) {

        // 1.获取请求头中的 token
        String header_token = request.getHeader("Authorization");

        // 2.获取登录时存在 session 中的 token
        String session_token = (String) request.getSession().getAttribute("access_token");

        // 3.判断 token 是否一致
        if (header_token.equals(session_token)) {

            // 值一致表示登录的是同一个账户且在登录状态
            // 1.获取用户 id
            Integer userId = (Integer) request.getSession().getAttribute("user_id");
            // 2.调用service，进行菜单信息查询
            ResponseResult responseResult = userService.getUserPermissions(userId);

            return responseResult;
        } else {

            // token 不一致
            ResponseResult responseResult = new ResponseResult(false, 400, "获取菜单信息失败", null);
            return responseResult;
        }
    }
}
