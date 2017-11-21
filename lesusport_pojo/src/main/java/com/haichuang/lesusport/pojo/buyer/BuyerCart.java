package com.haichuang.lesusport.pojo.buyer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.haichuang.lesusport.pojo.product.Sku;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车类
 */
public class BuyerCart implements Serializable {
    private static final long serialVersionUID = -2589579715064609307L;
    private List<BuyerItem> items = new ArrayList<>(0);

    public List<BuyerItem> getItems() {
        return items;
    }

    public void setItems(List<BuyerItem> items) {
        this.items = items;
    }

    public void addItem(Long skuId, Integer amount) {
        Sku sku = new Sku();
        BuyerItem item = new BuyerItem();
        sku.setId(skuId);
        item.setSku(sku);
        item.setAmount(amount);
        //判断添加的商品再购物车中是否已经存在
        if (items.contains(item)) {
            for (BuyerItem buyerItem : items) {
                if (buyerItem.equals(item)) {
                    buyerItem.setAmount(buyerItem.getAmount() + item.getAmount());
                }
            }
        } else {
            items.add(item);
        }
    }

    @JsonIgnore
    public Integer getProductAmount() {
        Integer result = 0;
        for (BuyerItem item : items) {
            result += item.getAmount();
        }
        return result;
    }

    // 商品金额
    @JsonIgnore
    public Float getProductPrice() {
        Float result = 0f;
        for (BuyerItem item : items) {
            result += item.getAmount() * item.getSku().getPrice();
        }
        return result;
    }

    //运费
    @JsonIgnore
    public Float getFee() {
        Float result = 0f;
        if (getProductPrice() < 99f) {
            result = 6f;
        }
        return result;
    }

    //总计
    @JsonIgnore
    public Float getTotalPrice() {
        return getProductPrice() + getFee();
    }
}
