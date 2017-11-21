package com.haichuang.lesusport.service.sku;

import com.haichuang.lesusport.dao.product.ColorDao;
import com.haichuang.lesusport.dao.product.SkuDao;
import com.haichuang.lesusport.pojo.product.Sku;
import com.haichuang.lesusport.pojo.product.SkuQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("skuService")
@Transactional
public class SkuServiceImpl implements SkuService {
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private ColorDao colorDao;

    @Override
    public List<Sku> listSkusByProductId(Long id) {
        SkuQuery skuQuery = new SkuQuery();
        skuQuery.createCriteria().andProductIdEqualTo(id);
        List<Sku> skus = skuDao.selectByExample(skuQuery);
        for (Sku sku : skus) {
            Long colorId = sku.getColorId();
            sku.setColor(colorDao.selectByPrimaryKey(colorId));
        }
        return skus;
    }

    @Override
    public void updateSkuById(Sku sku) {
        skuDao.updateByPrimaryKeySelective(sku);
    }
}
