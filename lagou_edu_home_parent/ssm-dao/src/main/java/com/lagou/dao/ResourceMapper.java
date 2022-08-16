package com.lagou.dao;

import com.lagou.domain.Resource;
import com.lagou.domain.ResourceVo;

import java.util.List;

/**
 * @author hhc19
 * @date 2022/8/15 22:53
 * @description
 */
public interface ResourceMapper {

    /*
        资源分页&多条件查询
     */
    public List<Resource> findAllResourceByPage(ResourceVo resourceVo);
}
