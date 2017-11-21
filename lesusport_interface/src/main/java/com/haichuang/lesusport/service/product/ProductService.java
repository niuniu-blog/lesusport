package com.haichuang.lesusport.service.product;

import cn.itcast.common.page.Pagination;
import com.haichuang.lesusport.pojo.product.Product;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * 商品接口
 */
public interface ProductService {
    /**
     * 分页查询+条件查询
     *
     * @param name
     * @param brandId
     * @param isShow
     * @return
     */
    Pagination pageQuery(String name, Long brandId, Boolean isShow, Integer pageNo);

    /**
     * 添加商品
     *
     * @param product
     */
    void saveProduct(Product product);

    /**
     * 上架商品
     *
     * @param ids
     */
    void showProduct(Long[] ids) throws IOException, SolrServerException;

    /**
     * 下架商品
     *
     * @param ids
     */
    void downProduct(Long[] ids) throws IOException, SolrServerException;

    /**
     * 批量删除商品
     *
     * @param ids
     */
    void removeProducts(Long[] ids);

    /**
     * 删除单个商品
     *
     * @param id
     * @return
     */
    void removeProduct(Long id);

    /**
     * 通过id获取商品
     *
     * @param id
     * @return
     */
    Product getProductById(Long id);

    /**
     * 更新商品
     *
     * @param product
     */
    void updateProduct(Product product);
}
