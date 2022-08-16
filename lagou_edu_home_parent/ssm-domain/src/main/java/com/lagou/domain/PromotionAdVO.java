package com.lagou.domain;

/*
    Vo 类就是专门用来接收前台传过来的参数的
 */
public class PromotionAdVO {

    //当前页
    private Integer currentPage;

    //每页显示的条数
    private Integer pageSize;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
