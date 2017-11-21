package com.haichuang.lesusport.service;

import cn.itcast.common.page.Pagination;
import com.haichuang.lesusport.dao.brand.BrandDao;
import com.haichuang.lesusport.dao.product.ProductDao;
import com.haichuang.lesusport.dao.product.SkuDao;
import com.haichuang.lesusport.pojo.brand.Brand;
import com.haichuang.lesusport.pojo.product.Product;
import com.haichuang.lesusport.pojo.product.ProductQuery;
import com.haichuang.lesusport.pojo.product.Sku;
import com.haichuang.lesusport.pojo.product.SkuQuery;
import com.haichuang.lesusport.service.portal.product.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.*;

@Service("searchService")
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SolrServer solrServer;
    @Autowired
    private Jedis jedis;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private SkuDao skuDao;

    public void saveProductToSolr(Long id) throws IOException, SolrServerException {
        SkuQuery skuQuery = new SkuQuery();
        skuQuery.createCriteria().andProductIdEqualTo(id);
        skuQuery.setOrderByClause("price asc");
        skuQuery.setPageNo(1);
        skuQuery.setPageSize(1);
        skuQuery.setFields("price");
        Product product = productDao.selectByPrimaryKey(id);
        SolrInputDocument doc = new SolrInputDocument();
        //id
        doc.addField("id", product.getId());
        //名称
        doc.addField("name_ik", product.getName());
        //图片地址
        doc.addField("url", product.getImgUrl());
        //价格
        List<Sku> skus = skuDao.selectByExample(skuQuery);
        doc.addField("price", skus.get(0).getPrice());
        //品牌
        doc.addField("brandId", product.getBrandId());
        //分词时间
        doc.addField("createtime", new Date());
        solrServer.add(doc);
        solrServer.commit();
    }

    @Override
    public Pagination getPagination(String keyword, Long brandId, Integer pageNo, String price) throws SolrServerException {
        StringBuilder sb = new StringBuilder();
        ProductQuery productQuery = new ProductQuery();
        productQuery.setPageNo(Pagination.cpn(pageNo));
        productQuery.setPageSize(12);
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.set("df", "name_ik");
        //查询关键字
        solrQuery.setQuery(keyword);
        sb.append("keyword=").append(keyword);
        solrQuery.setRows(productQuery.getPageSize());
        solrQuery.setStart(productQuery.getStartRow());
        solrQuery.setSort("price", SolrQuery.ORDER.asc);
        solrQuery.set("fl", "id,name_ik,price,url");
        //设置高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("name_ik");
        solrQuery.setHighlightSimplePre("<span style='color:red'>");
        solrQuery.setHighlightSimplePost("</span>");
        //设置过滤条件
        if (brandId != null) {
            solrQuery.addFilterQuery("brandId : " + brandId);
            sb.append("&brandId=").append(brandId);
        }
        if (price != null) {
            String[] split = price.split("-");
            if (split.length > 1) {
                solrQuery.addFilterQuery("price : [ " + split[0] + " TO " + split[1] + " ]");
                sb.append("&price=").append(price);
            } else {
                solrQuery.addFilterQuery("price : [ " + split[0] + " TO *]");
                sb.append("&price=").append(price);
            }
        }
        //取回结果
        QueryResponse response = solrServer.query(solrQuery);
        Map<String, Map<String, List<String>>> hl = response.getHighlighting();
        SolrDocumentList results = response.getResults();
        long numFound = results.getNumFound();
        Pagination pagination = new Pagination(productQuery.getPageNo(), productQuery.getPageSize(), (int) numFound);
        List<Product> products = new ArrayList<>();
        Product product;
        for (SolrDocument doc : results) {
            product = new Product();
            String id = (String) doc.get("id");
            Map<String, List<String>> map = hl.get(id);
            product.setId(Long.parseLong(id));
            //取出高亮的字段
            String name = map.get("name_ik").get(0);
            product.setName(name);
            Float p = (Float) doc.get("price");
            product.setPrice(p);
            String url = (String) doc.get("url");
            product.setImgUrl(url);
            products.add(product);
        }
        pagination.setList(products);
        pagination.pageView("/product/search", sb.toString());
        return pagination;
    }

    @Override
    public List<Brand> listBrands() {
        List<Brand> brands;
        Brand brand;
        Map<String, String> map = jedis.hgetAll("brand");
        if (map != null && map.size() > 0) {
            brands = new ArrayList<>();
            Set<String> keys = map.keySet();
            for (String key : keys) {
                brand = new Brand();
                brand.setId(Long.parseLong(key));
                brand.setName(map.get(key));
                brands.add(brand);
            }
            return brands;
        }
        brands = brandDao.listIdAndNameFromBrandAndIsDisplayIsTrue();
        for (Brand brand1 : brands) {
            jedis.hset("brand", String.valueOf(brand1.getId()), brand1.getName());
            //每一周自动更新缓存一次
            jedis.expire("brand", 60 * 60 * 24 * 7);
        }
        return brands;
    }
}
