package com.haichuang.lesusport.service;

import com.haichuang.lesusport.dao.product.ColorDao;
import com.haichuang.lesusport.dao.product.ProductDao;
import com.haichuang.lesusport.dao.product.SkuDao;
import com.haichuang.lesusport.pojo.product.Product;
import com.haichuang.lesusport.pojo.product.Sku;
import com.haichuang.lesusport.pojo.product.SkuQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("cmsService")
@Transactional
public class CmsServiceImpl implements CmsService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private ColorDao colorDao;

    @Override
    public Product getProductById(Long id) {
        return productDao.selectByPrimaryKey(id);
    }

    @Override
    public List<Sku> listSkuByProductId(Long id) {
        SkuQuery skuQuery = new SkuQuery();
        skuQuery.createCriteria().andProductIdEqualTo(id).andStockGreaterThan(0);
        List<Sku> skus = skuDao.selectByExample(skuQuery);
        for (Sku sku : skus) {
            sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId()));
        }
        return skus;
    }
}
