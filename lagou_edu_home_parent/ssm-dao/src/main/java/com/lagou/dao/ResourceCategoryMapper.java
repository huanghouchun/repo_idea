package com.lagou.dao;

import com.lagou.domain.ResourceCategory;

import java.util.List;

/**
 * @author hhc19
 * @date 2022/8/15 22:22
 * @description
 */
public interface ResourceCategoryMapper {

    /*
        查询所有资源分类信息
     */
    public List<ResourceCategory> findAllResourceCategory();
}
