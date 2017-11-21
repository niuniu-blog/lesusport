package com.haichuang.lesusport.service.brand;

import cn.itcast.common.page.Pagination;
import com.haichuang.lesusport.dao.brand.BrandDao;
import com.haichuang.lesusport.pojo.brand.Brand;
import com.haichuang.lesusport.vojo.BrandVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service("brandService")
@Transactional
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private Jedis jedis;

    @Override
    public Pagination pageBrand(BrandVo brandVo) {
        StringBuilder sb = new StringBuilder();
        Integer currentPage = brandVo.getCurrentPage();
        Integer pageNo = brandVo.getPageNo();
        if (currentPage == null) {
            currentPage = 1;
            if (pageNo != null) {
                currentPage = pageNo;
            }
        }
        String name = brandVo.getName();
        if (StringUtils.isNotBlank(name)) {
            sb.append("name=").append(name);
        }
        Integer isDisplay = brandVo.getIsDisplay();
        if (isDisplay != null) {
            sb.append("&isDisplay=").append(isDisplay);
        }
        brandVo.setPageSize(10);
        Integer selectCount = brandDao.countPage(brandVo);
        Pagination pagination = new Pagination(currentPage, brandVo.getPageSize(), selectCount);
        //修复页码
        brandVo.setCurrentPage(pagination.getPageNo());
        brandVo.setStartIndex((brandVo.getCurrentPage() - 1) * brandVo.getPageSize());
        List<Brand> brands = brandDao.pageQuery(brandVo);
        pagination.setList(brands);
        pagination.pageView("/brand/list", sb.toString());
        return pagination;
    }

    @Override
    public Brand getBrandById(Integer id) {
        return brandDao.getBrandById(id);
    }

    @Override
    public void updataBrandById(Brand brand) {
        brandDao.updateBrandById(brand);
        jedis.hset("brand", String.valueOf(brand.getId()), brand.getName());
    }

    @Override
    public void removeBrands(Long[] ids) {
        brandDao.removeBrands(ids);
        for (Long id : ids) {
            jedis.hdel("brand", String.valueOf(id));
        }
    }

    @Override
    public List<Brand> listBrands() {
        return brandDao.listBrands();
    }

    @Override
    public void removeBrand(Long id) {
        brandDao.removeBrand(id);
        jedis.hdel("brand", String.valueOf(id));
    }

    @Override
    public void saveBrand(Brand brand) {
        brandDao.saveBrand(brand);
        jedis.hset("brand", String.valueOf(brand.getId()), brand.getName());
    }
}
