package com.haichuang.lesusport.service.login;

import com.haichuang.lesusport.pojo.buyer.BuyerCart;
import com.haichuang.lesusport.pojo.product.Sku;
import com.haichuang.lesusport.pojo.user.Buyer;

/**
 * 用户业务接口
 */
public interface BuyerService {
    /**
     * 通过用户名获取用户
     */
    Buyer getBuyerByUsername(String username);

    /**
     * 合并cookie中的购物车
     *
     * @param cart
     * @param username
     */
    void saveBuyerToRedis(BuyerCart cart, String username);

    /**
     * 从redis中获取购物车
     *
     * @param username
     */
    BuyerCart getBuyerCartFromRedis(String username);

    /**
     * 通过id获取库存
     *
     * @param id
     * @return
     */
    Sku getSkuBySkuId(Long id);
}