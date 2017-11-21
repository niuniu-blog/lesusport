package com.haichuang.lesusport.vojo;

import com.haichuang.lesusport.pojo.brand.Brand;

import java.io.Serializable;
import java.util.List;

/**
 * 品牌vo类
 */
public class BrandVo implements Serializable {
    private static final long serialVersionUID = -5249386077705366368L;
    //品牌集合
    private List<Brand> rows;
    //查询条件 品牌名
    private String name;
    //查询条件 是否可用
    private Integer isDisplay;
    //开始索引
    private Integer startIndex;
    private Integer currentPage;
    //每页显示条数
    private Integer pageSize;
    private Integer pageNo;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public List<Brand> getRows() {
        return rows;
    }

    public void setRows(List<Brand> rows) {
        this.rows = rows;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Integer isDisplay) {
        this.isDisplay = isDisplay;
    }

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

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }
}
