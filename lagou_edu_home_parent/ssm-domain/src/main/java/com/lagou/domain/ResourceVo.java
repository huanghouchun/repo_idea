package com.lagou.domain;

/**
 * @author hhc19
 * @date 2022/8/15 22:57
 * @description
 */
public class ResourceVo {

    private Integer currentPage; // 当前页

    private Integer pageSize; // 每页显示条数

    private String name; // 资源名称

    private Integer categoryId; // 资源分类 id

    private String url; // 资源路径

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
