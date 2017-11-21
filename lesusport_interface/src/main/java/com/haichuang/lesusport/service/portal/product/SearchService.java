package com.haichuang.lesusport.service.portal.product;

import com.haichuang.lesusport.pojo.brand.Brand;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;
import java.util.List;

public interface SearchService {
    /**
     * 分页条件查询 从索引库中
     *
     * @param keyword
     * @param brandId
     * @param pageNo
     * @param price
     * @return
     * @throws SolrServerException
     */
    cn.itcast.common.page.Pagination getPagination(String keyword, Long brandId, Integer pageNo, String price) throws SolrServerException;

    /**
     * 从缓存中查询品牌
     *
     * @return
     */
    List<Brand> listBrands();

    /**
     * 保存商品到索引库
     *
     * @param id
     * @throws IOException
     * @throws SolrServerException
     */
    void saveProductToSolr(Long id) throws IOException, SolrServerException;
}
