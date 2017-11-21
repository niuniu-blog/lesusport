package com.haichuang.lesusport.service.brand;

import cn.itcast.common.page.Pagination;
import com.haichuang.lesusport.pojo.brand.Brand;
import com.haichuang.lesusport.vojo.BrandVo;

import java.util.List;

public interface BrandService {
    /**
     * 分页+条件查询品牌列表
     *
     * @param brandVo
     * @return
     */
    Pagination pageBrand(BrandVo brandVo);

    /**
     * 通过id获取brand
     *
     * @param id
     * @return
     */
    Brand getBrandById(Integer id);

    /**
     * 更新品牌信息
     */
    void updataBrandById(Brand brand);

    /**
     * 批量删除品牌
     */
    void removeBrands(Long[] ids);

    /**
     * 查询所有品牌
     */
    List<Brand> listBrands();

    /**
     * 删除单个品牌
     *
     * @param id
     */
    void removeBrand(Long id);

    /**
     * 添加品牌
     *
     * @param brand
     */
    void saveBrand(Brand brand);
}
