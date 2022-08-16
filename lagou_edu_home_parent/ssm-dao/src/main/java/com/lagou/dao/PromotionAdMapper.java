package com.lagou.dao;

import com.lagou.domain.PromotionAd;

import java.util.List;

/**
 * @author hhc19
 * @date 2022/8/13 22:37
 * @description
 */
public interface PromotionAdMapper {

    /*
        分页查询广告信息
     */
    public List<PromotionAd> findAllPromotionAdByPage();

    /*
        广告状态动态上下线
        上架 1 下架 0
     */
    public void updatePromotionAdStatus(PromotionAd promotionAd);

    /*
        新增广告
     */
    public void savePromotionAd(PromotionAd promotionAd);

    /*
        修改广告
     */
    public void updatePromotionAd(PromotionAd promotionAd);

    /**
     * 根据id查询广告信息
     *
     */

    public PromotionAd findPromotionAdById(int id);
}
