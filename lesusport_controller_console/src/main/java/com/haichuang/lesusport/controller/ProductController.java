package com.haichuang.lesusport.controller;

import cn.itcast.common.page.Pagination;
import com.haichuang.lesusport.pojo.brand.Brand;
import com.haichuang.lesusport.pojo.product.Product;
import com.haichuang.lesusport.service.brand.BrandService;
import com.haichuang.lesusport.service.color.ColorService;
import com.haichuang.lesusport.service.product.ProductService;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 商品管理
 */
@Controller
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ColorService colorService;

    /**
     * 更新商品及库存
     *
     * @param product
     * @return
     */
    @RequestMapping("updateProduct")
    public String updateProduct(Product product) {
        productService.updateProduct(product);
        return "product/list";
    }

    /**
     * 更新商品准备工作
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("updateProductUi/{id}")
    public String updateProductUi(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("brands", brandService.listBrands());
        model.addAttribute("colors", colorService.listColors());
        model.addAttribute("product", product);
        List<String> c = Arrays.asList(product.getColors().split(","));
        List<String> s = Arrays.asList(product.getSizes().split(","));
        List<String> imgs = Arrays.asList(product.getImgUrl().split(","));
        model.addAttribute("c", c);
        model.addAttribute("s", s);
        model.addAttribute("imgs", imgs);
        return "product/edit";
    }

    /**
     * 删除单个商品
     *
     * @param id
     * @return
     */
    @RequestMapping("removeProduct/{id}")
    public String removeProduct(@PathVariable Long id) {
        productService.removeProduct(id);
        return "forward:/product/list";
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("removeProducts")
    public String removeProducts(Long[] ids) {
        productService.removeProducts(ids);
        return "forward:/product/list";
    }

    /**
     * 下架商品
     *
     * @param ids
     * @return
     */
    @RequestMapping("downProduct")
    public String downProduct(Long[] ids) throws IOException, SolrServerException {
        productService.downProduct(ids);
        return "forward:/product/list";
    }

    /**
     * 上架商品
     *
     * @param ids 商品id
     * @return
     */
    @RequestMapping("showProduct")
    public String showProduct(Long[] ids) throws IOException, SolrServerException {
        productService.showProduct(ids);
        return "forward:/product/list";
    }

    /**
     * 添加商品
     *
     * @param product
     * @return
     */
    @RequestMapping("saveProduct")
    public String saveProduct(Product product) {
        productService.saveProduct(product);
        return "redirect:/product/list";
    }

    /**
     * 前往添加页面
     *
     * @return
     */
    @RequestMapping("toAdd")
    public String addUI(Model model) {
        model.addAttribute("brands", brandService.listBrands());
        model.addAttribute("colors", colorService.listColors());
        return "product/add";
    }

    /**
     * 商品列表页面
     *
     * @return
     */
    @RequestMapping("list")
    public String productList(String name, Long brandId, Boolean isShow, Integer pageNo, Model model) {
        Pagination pagination = productService.pageQuery(name, brandId, isShow, pageNo);
        List<Brand> brands = brandService.listBrands();
        model.addAttribute("name", name);
        model.addAttribute("isShow", isShow);
        model.addAttribute("pagination", pagination);
        model.addAttribute("brands", brands);
        model.addAttribute("brandId", brandId);
        return "product/list";
    }
}
