package com.lagou.service;

import com.lagou.domain.PromotionSpace;

import java.util.List;

/**
 * @author hhc19
 * @date 2022/8/13 20:23
 * @description
 */
public interface PromotionSpaceService {

    /*
        查询所有的广告位
     */
    public List<PromotionSpace> findAllPromotionSpace();

    /*
        添加广告位
     */
    public void savePromotionSpace(PromotionSpace promotionSpace);

    /*
        修改广告位
     */
    public void updatePromotionSpace(PromotionSpace promotionSpace);

    /*
        回显广告位名称
        根据 ID 查询广告位信息
     */
    public PromotionSpace findPromotionSpaceById(Integer id);
}
