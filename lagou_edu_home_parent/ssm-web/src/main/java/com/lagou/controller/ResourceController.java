package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.Resource;
import com.lagou.domain.ResourceVo;
import com.lagou.domain.ResponseResult;
import com.lagou.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hhc19
 * @date 2022/8/15 23:08
 * @description
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /*
        资源分页&多条件查询
     */
    @RequestMapping("/findAllResource")
    public ResponseResult findAllResource(@RequestBody ResourceVo resourceVo) {

        PageInfo<Resource> pageInfo = resourceService.findAllResourceByPage(resourceVo);

        return new ResponseResult(true, 200, "资源信息分页&多条件查询成功", pageInfo);
    }
}
