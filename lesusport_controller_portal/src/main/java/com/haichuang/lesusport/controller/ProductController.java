package com.haichuang.lesusport.controller;

import cn.itcast.common.page.Pagination;
import com.haichuang.lesusport.pojo.brand.Brand;
import com.haichuang.lesusport.pojo.product.Color;
import com.haichuang.lesusport.pojo.product.Product;
import com.haichuang.lesusport.pojo.product.Sku;
import com.haichuang.lesusport.service.AdService;
import com.haichuang.lesusport.service.CmsService;
import com.haichuang.lesusport.service.portal.product.SearchService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("product")
public class ProductController {
    @Autowired
    private SearchService searchService;
    @Autowired
    private CmsService cmsService;
    @Autowired
    private AdService adService;

    /**
     * 商品详情页
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("detail/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Product product = cmsService.getProductById(id);
        List<Sku> skus = cmsService.listSkuByProductId(id);
        model.addAttribute("product", product);
        model.addAttribute("skus", skus);
        Set<Color> colors = new HashSet<>();
        for (Sku sku : skus) {
            colors.add(sku.getColor());
        }
        model.addAttribute("colors", colors);
        return "product";
    }

    /**
     * 从索引库中分页查询+条件查询
     *
     * @param keyword
     * @param brandId
     * @param pageNo
     * @param price
     * @param model
     * @return
     * @throws SolrServerException
     */
    @RequestMapping("search")
    public String search(String keyword, Long brandId, Integer pageNo, String price, Model model) throws SolrServerException {
        List<Brand> brands = searchService.listBrands();
        Pagination pagination = searchService.getPagination(keyword, brandId, pageNo, price);
        model.addAttribute("brands", brands);
        model.addAttribute("pagination", pagination);
        model.addAttribute("keyword", keyword);
        model.addAttribute("brandId", brandId);
        model.addAttribute("price", price);
        Map<String, String> map = new LinkedHashMap<>();
        if (brandId != null) {
            for (Brand brand : brands) {
                if (brandId == brand.getId()) {
                    map.put("品牌", brand.getName());
                }
            }
        }
        if (price != null) {
            if (price.split("-").length > 1) {
                map.put("价格", price);
            } else {
                map.put("价格", price + "以上");
            }
        }
        model.addAttribute("map", map);
        return "search";
    }

    /**
     * 去欢迎页
     *
     * @return
     */
    @RequestMapping("toIndex")
    public String toIndex(Model model) {
        model.addAttribute("ads", adService.getAdsJson(89L));
        return "index";
    }

}
