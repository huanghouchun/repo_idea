package com.lagou.service;

import com.lagou.domain.ResourceCategory;

import java.util.List;

/**
 * @author hhc19
 * @date 2022/8/15 22:23
 * @description
 */
public interface ResourceCategoryService {

    /*
        查询所有资源分类信息
     */
    public List<ResourceCategory> findAllResourceCategory();
}
