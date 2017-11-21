package com.haichuang.lesusport.service.product;

import cn.itcast.common.page.Pagination;
import com.haichuang.lesusport.dao.product.ProductDao;
import com.haichuang.lesusport.dao.product.SkuDao;
import com.haichuang.lesusport.pojo.product.Product;
import com.haichuang.lesusport.pojo.product.ProductQuery;
import com.haichuang.lesusport.pojo.product.Sku;
import com.haichuang.lesusport.pojo.product.SkuQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品业务
 */
@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private Jedis jedis;
    @Autowired
    private SolrServer solrServer;
    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public Pagination pageQuery(String name, Long brandId, Boolean isShow, Integer pageNo) {
        ProductQuery productQuery = new ProductQuery();
        productQuery.setPageSize(10);
        //....
        productQuery.setPageNo(Pagination.cpn(pageNo));
        StringBuilder sb = new StringBuilder();
        ProductQuery.Criteria criteria = productQuery.createCriteria();
        criteria.andIsDelEqualTo(true);
        if (name != null) {
            criteria.andNameLike("%" + name + "%");
            sb.append("name=").append(name);
        }
        if (brandId != null) {
            criteria.andBrandIdEqualTo(brandId);
            sb.append("&brandId=").append(brandId);
        }
        if (isShow != null) {
            criteria.andIsShowEqualTo(isShow);
            sb.append("&isShow=").append(isShow);
        } else {
            criteria.andIsShowEqualTo(false);
            sb.append("&isShow=").append(false);
        }
        int count = productDao.countByExample(productQuery);
        Pagination pagination = new Pagination(productQuery.getPageNo(), productQuery.getPageSize(), count);
        //修复页码
        productQuery.setPageNo(pagination.getPageNo());
        productQuery.setOrderByClause("id desc");
        List<Product> products = productDao.selectByExample(productQuery);
        pagination.setList(products);
        String url = "/product/list";
        pagination.pageView(url, sb.toString());
        return pagination;
    }

    @Override
    public void saveProduct(Product product) {
        //设置商品参数：
        Long pid = jedis.incr("pid");
        product.setId(pid);
        product.setIsShow(false);
        product.setIsDel(true);
        product.setCreateTime(new Date());
        productDao.insertSelective(product);
        Sku sku = null;
        String colors = product.getColors();
        String sizes = product.getSizes();
        if (colors != null) {
            for (String color : colors.split(",")) {
                if (sizes != null) {
                    for (String size : sizes.split(",")) {
                        sku = new Sku();
                        sku.setColorId(Long.parseLong(color));
                        sku.setCreateTime(new Date());
                        sku.setSize(size);
                        sku.setProductId(product.getId());
                        sku.setDeliveFee(15F);
                        sku.setPrice(0F);
                        sku.setUpperLimit(0);
                        sku.setStock(0);
                        skuDao.insertSelective(sku);
                    }
                }
            }
        }
    }

    @Override
    public void showProduct(Long[] ids) throws IOException, SolrServerException {
        Product product;
        for (final Long id : ids) {
            product = new Product();
            product.setIsShow(true);
            product.setId(id);
            productDao.updateByPrimaryKeySelective(product);
            //发送消息
            jmsTemplate.send(new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(String.valueOf(id));
                }
            });
        }
    }

    @Override
    public void downProduct(Long[] ids) throws IOException, SolrServerException {
        Product product;
        List<String> allId = new ArrayList<>();
        for (Long id : ids) {
            product = new Product();
            product.setIsShow(false);
            product.setId(id);
            productDao.updateByPrimaryKeySelective(product);
            allId.add(String.valueOf(id));
        }
        solrServer.deleteById(allId);
        solrServer.commit();
    }

    @Override
    public void removeProducts(Long[] ids) {
        Product product;
        for (Long id : ids) {
            product = new Product();
            product.setIsDel(false);
            product.setId(id);
            productDao.updateByPrimaryKeySelective(product);
        }
    }

    @Override
    public void removeProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        product.setIsDel(false);
        productDao.updateByPrimaryKeySelective(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productDao.selectByPrimaryKey(id);
    }

    //TODO 可能会有问题
    @Override
    public void updateProduct(Product product) {
        productDao.updateByPrimaryKeySelective(product);
        Sku sku = null;
        String colors = product.getColors();
        String sizes = product.getSizes();
        SkuQuery skuQuery = new SkuQuery();
        SkuQuery.Criteria criteria = skuQuery.createCriteria();
        criteria.andProductIdEqualTo(product.getId());
        List<Sku> skus = skuDao.selectByExample(skuQuery);
        //判断是否有多余的库存
        for (Sku skus1 : skus) {
            Long colorId = skus1.getColorId();
            //判断该颜色的库存是否还存在
            if (product.getColors().contains(String.valueOf(colorId))) {
                //存在
                //判断该尺码的库存是否存在
                if (product.getSizes().contains(skus1.getSize())) {
                    //存在
                } else {
                    //不存在,移除库存
                    skuDao.deleteByPrimaryKey(skus1.getId());
                }
            } else {
                //不存在，删除该颜色的库存
                skuDao.deleteByPrimaryKey(skus1.getId());
            }
        }
        //判断是否有需要增加的库存
        if (colors != null) {
            for (String color : colors.split(",")) {
                if (sizes != null) {
                    for (String size : sizes.split(",")) {
                        boolean flag = true;
                        //开始匹配所有颜色
                        for (Sku skus1 : skus) {
                            Long colorId = skus1.getColorId();
                            //颜色匹配
                            if (Long.parseLong(color) == colorId) {
                                //开始匹配所有尺码
                                for (Sku skus2 : skus) {
                                    //尺码匹配 颜色匹配
                                    if (skus2.getSize().equals(size) && skus2.getColorId() == Long.parseLong(color)) {
                                        flag = false;
                                    }
                                }
                            } else {
                                //存在不匹配的颜色
                                flag = false;
                                //没有匹配的颜色
                            }//便利一遍已经存在的颜色后，发现没有此颜色 加入该颜色和该尺码
                            if (flag) {
                                sku = new Sku();
                                sku.setColorId(Long.parseLong(color));
                                sku.setSize(size);
                                skuDao.insertSelective(sku);
                            }
                        }
                    }
                }
            }
        }
    }
}
