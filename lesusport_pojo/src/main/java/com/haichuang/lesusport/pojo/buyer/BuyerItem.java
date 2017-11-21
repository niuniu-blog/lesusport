package com.haichuang.lesusport.pojo.buyer;

import com.haichuang.lesusport.pojo.product.Sku;

import java.io.Serializable;

/**
 * 购物车详情类
 */
public class BuyerItem implements Serializable {
    private static final long serialVersionUID = -443807612228105079L;
    private Sku sku;
    private Boolean have;
    private Integer amount;

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    public Boolean getHave() {
        return have;
    }

    public void setHave(Boolean have) {
        this.have = have;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BuyerItem item = (BuyerItem) o;

        return sku != null ? sku.getId().equals(item.sku.getId()) : item.sku == null;
    }

    @Override
    public int hashCode() {
        return sku != null ? sku.hashCode() : 0;
    }
}
