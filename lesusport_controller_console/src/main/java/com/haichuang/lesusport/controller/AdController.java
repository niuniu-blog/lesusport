package com.haichuang.lesusport.controller;

import com.haichuang.lesusport.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("ad")
public class AdController {
    @Autowired
    private AdService adService;

    /**
     * 广告详情
     *
     * @return
     */
    @RequestMapping("list/{id}")
    public String list(@PathVariable Long id, Model model) {
        model.addAttribute("list", adService.listAdByPositionId(id));
        return "ad/list";
    }
}
