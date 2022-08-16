package com.lagou.dao;

import com.lagou.domain.PromotionSpace;

import java.util.List;

/**
 * @author hhc19
 * @date 2022/8/13 20:04
 * @description
 */
public interface PromotionSpaceMapper {

    /*
        获取所有广告位
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
