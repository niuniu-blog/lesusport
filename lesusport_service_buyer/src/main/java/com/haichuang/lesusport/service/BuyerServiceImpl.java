package com.haichuang.lesusport.service;

import com.haichuang.lesusport.constans.Constans;
import com.haichuang.lesusport.dao.product.ColorDao;
import com.haichuang.lesusport.dao.product.ProductDao;
import com.haichuang.lesusport.dao.product.SkuDao;
import com.haichuang.lesusport.dao.user.BuyerDao;
import com.haichuang.lesusport.pojo.buyer.BuyerCart;
import com.haichuang.lesusport.pojo.buyer.BuyerItem;
import com.haichuang.lesusport.pojo.product.Sku;
import com.haichuang.lesusport.pojo.user.Buyer;
import com.haichuang.lesusport.pojo.user.BuyerQuery;
import com.haichuang.lesusport.service.login.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

@Service("buyerService")
@Transactional
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private BuyerDao buyerDao;
    @Autowired
    private Jedis jedis;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private ColorDao colorDao;
    @Autowired
    private ProductDao productDao;

    @Override
    public Buyer getBuyerByUsername(String username) {
        BuyerQuery buyerQuery = new BuyerQuery();
        buyerQuery.createCriteria().andUsernameEqualTo(username);
        List<Buyer> buyers = buyerDao.selectByExample(buyerQuery);
        if (buyers != null && buyers.size() > 0) {
            return buyers.get(0);
        }
        return null;
    }

    @Override
    public void saveBuyerToRedis(BuyerCart cart, String username) {
        //添加到redis中
        if (cart != null && cart.getItems().size() > 0) {
            for (BuyerItem item : cart.getItems()) {
                jedis.hincrBy(Constans.BUYER_CART + ":" + username, String.valueOf(item.getSku().getId()), item.getAmount());
            }
        }
    }

    @Override
    public BuyerCart getBuyerCartFromRedis(String username) {
        BuyerCart buyerCart = null;
        Map<String, String> map = jedis.hgetAll(Constans.BUYER_CART + ":" + username);
        if (map != null && map.size() > 0) {
            buyerCart = new BuyerCart();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                buyerCart.addItem(Long.parseLong(entry.getKey()), Integer.parseInt(entry.getValue()));
            }
        }
        return buyerCart;
    }

    @Override
    public Sku getSkuBySkuId(Long id) {
        Sku sku = skuDao.selectByPrimaryKey(id);
        sku.setProduct(productDao.selectByPrimaryKey(sku.getProductId()));
        sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId()));
        return sku;
    }
}
