package com.haichuang.lesusport.service;

import com.haichuang.lesusport.pojo.product.Product;
import com.haichuang.lesusport.pojo.product.Sku;

import java.util.List;

public interface CmsService {
    /**
     * 通过商品id查询商品
     *
     * @param id
     * @return
     */
    Product getProductById(Long id);

    /**
     * 通过商品id查询库存
     *
     * @param id
     * @return
     */
    List<Sku> listSkuByProductId(Long id);
}
