package com.haichuang.lesusport.dao.brand;

import com.haichuang.lesusport.pojo.brand.Brand;
import com.haichuang.lesusport.vojo.BrandVo;

import java.util.List;

public interface BrandDao {
    /**
     * 分页条件查询
     *
     * @param brandVo
     * @return
     */
    List<Brand> pageQuery(BrandVo brandVo);

    /**
     * 计算总页数
     *
     * @param brandVo
     * @return
     */
    Integer countPage(BrandVo brandVo);

    /**
     * 根据id获取品牌
     *
     * @param id
     * @return
     */
    Brand getBrandById(Integer id);

    /**
     * 修改品牌
     */
    void updateBrandById(Brand brand);

    /**
     * 批量删除品牌
     */
    void removeBrands(Long[] ids);

    /**
     * 查询所有品牌
     *
     * @return
     */
    List<Brand> listBrands();

    /**
     * 删除单个品牌
     *
     * @param id
     */
    void removeBrand(Long id);

    /**
     * 保存品牌
     *
     * @param brand
     */
    void saveBrand(Brand brand);

    /**
     * 查询可用品牌的id和名字
     *
     * @return
     */
    List<Brand> listIdAndNameFromBrandAndIsDisplayIsTrue();
}
