package com.haichuang.lesusport.service.sku;

import com.haichuang.lesusport.pojo.product.Sku;

import java.util.List;

public interface SkuService {
    /**
     * 根据商品id查找所有库存
     *
     * @param id
     * @return
     */
    List<Sku> listSkusByProductId(Long id);

    /**
     * 根据id修改库存
     *
     * @param sku
     */
    void updateSkuById(Sku sku);
}
