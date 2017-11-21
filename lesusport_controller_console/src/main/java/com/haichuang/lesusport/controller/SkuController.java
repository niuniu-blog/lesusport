package com.haichuang.lesusport.controller;

import com.haichuang.lesusport.pojo.product.Sku;
import com.haichuang.lesusport.service.sku.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequestMapping("sku")
@Controller
public class SkuController {
    @Autowired
    private SkuService skuService;

    @RequestMapping("updateSku")
    public void updateSku(Sku sku, HttpServletResponse response) throws IOException {
        try {
            skuService.updateSkuById(sku);
            response.getWriter().write("1");
        } catch (Exception e) {
            response.getWriter().write("0");
        }
    }

    /**
     * 库存页面
     *
     * @return
     */
    @RequestMapping("list/{id}")
    public String list(@PathVariable Long id, Model model) {
        List<Sku> skuServiceSkuByProductId = skuService.listSkusByProductId(id);
        model.addAttribute("skus", skuServiceSkuByProductId);
        return "sku/list";
    }
}
