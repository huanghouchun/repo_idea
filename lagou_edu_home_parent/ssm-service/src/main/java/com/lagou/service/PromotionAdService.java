package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;

/**
 * @author hhc19
 * @date 2022/8/13 22:58
 * @description
 */
public interface PromotionAdService {

    /*
        分页查询广告信息
     */
    public PageInfo<PromotionAd> findAllPromotionAdByPage(PromotionAdVO promotionAdVO);

    /*
        广告动态上下线
     */
    public void updatePromotionAdStatus(int id, int status);

    /*
       新增广告
    */
    public void savePromotionAd(PromotionAd promotionAd);

    /*
        修改广告
     */
    public void updatePromotionAd(PromotionAd promotionAd);

    /*
        根据id回显广告信息
     */
    public PromotionAd findPromotionAdById(int id);
}
