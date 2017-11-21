package com.haichuang.lesusport.controller;

import cn.itcast.common.page.Pagination;
import com.haichuang.lesusport.pojo.brand.Brand;
import com.haichuang.lesusport.service.brand.BrandService;
import com.haichuang.lesusport.vojo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 品牌
 */
@Controller
@RequestMapping("brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 添加
     *
     * @return
     */
    @RequestMapping("add")
    public String add(Brand brand) {
        brandService.saveBrand(brand);
        return "redirect:/brand/list";
    }

    /**
     * 前往添加页面
     *
     * @return
     */
    @RequestMapping("toadd")
    public String addUI() {
        return "brand/add";
    }

    @RequestMapping("removeBrand/{id}")
    public String removeBrand(@PathVariable Long id) {
        brandService.removeBrand(id);
        return "forward:/brand/list";
    }

    /**
     * 品牌列表
     *
     * @return
     */
    @RequestMapping("list")
    public String list(BrandVo brandVo, Model model) {
        Pagination pagination = brandService.pageBrand(brandVo);
        model.addAttribute("pagination", pagination);
        model.addAttribute("name", brandVo.getName());
        model.addAttribute("isDisplay", brandVo.getIsDisplay());
        return "brand/list";
    }

    /**
     * 通过id获取品牌
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("toEditor/{id}")
    public String toEditor(@PathVariable Integer id, Model model) {
        Brand brand = brandService.getBrandById(id);
        model.addAttribute("brand", brand);
        return "brand/edit";
    }

    /**
     * 更新品牌
     *
     * @param brand
     * @return
     */
    @RequestMapping("update")
    public String updateBrand(Brand brand) {
        brandService.updataBrandById(brand);
        return "redirect:/brand/list";
    }

    /**
     * 批量删除品牌
     */
    @RequestMapping("removeBrands")
    public String removeBrands(Long[] ids) {
        brandService.removeBrands(ids);
        return "forward:/brand/list";
    }
}
