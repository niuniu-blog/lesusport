package com.haichuang.lesusport.service.quartz;

import com.haichuang.lesusport.dao.product.ProductDao;
import com.haichuang.lesusport.pojo.product.Color;
import com.haichuang.lesusport.pojo.product.Product;
import com.haichuang.lesusport.pojo.product.ProductQuery;
import com.haichuang.lesusport.pojo.product.Sku;
import com.haichuang.lesusport.service.CmsService;
import com.haichuang.lesusport.service.StaticPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

//TODO 莫名其妙，触发两次
@Component("freeMarkerQuartz")
public class FreeMarkerQuartz {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private StaticPageService staticPageService;
    @Autowired
    private CmsService cmsService;

    public void autoFreeMarkerProduct() {
        ProductQuery productQuery = new ProductQuery();
        productQuery.createCriteria().andIsShowEqualTo(true);
        List<Product> products = productDao.selectByExample(productQuery);
        Map<String, Object> root;
        for (Product product : products) {
            root = new HashMap<>();
            List<Sku> skus = cmsService.listSkuByProductId(product.getId());
            root.put("product", product);
            root.put("skus", skus);
            Set<Color> colors = new HashSet<>();
            for (Sku sku : skus) {
                colors.add(sku.getColor());
            }
            root.put("colors", colors);
            staticPageService.index(root, product.getId());
        }
    }

}
