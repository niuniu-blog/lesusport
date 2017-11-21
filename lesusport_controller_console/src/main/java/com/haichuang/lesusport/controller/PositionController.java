package com.haichuang.lesusport.controller;

import com.haichuang.lesusport.pojo.ad.Position;
import com.haichuang.lesusport.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("position")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @RequestMapping("tree")
    public String tree(String root, Model model) {
        List<Position> positions;
        if ("source".equals(root)) {
            positions = positionService.listPositionnByParentId(0L);
        } else {
            positions = positionService.listPositionnByParentId(Long.parseLong(root));
        }
        model.addAttribute("list", positions);
        return "position/tree";
    }

    @RequestMapping("list/{id}")
    public String list(@PathVariable Long id, Model model) {
        List<Position> positions = positionService.listPositionnByParentId(id);
        model.addAttribute("list", positions);
        return "position/list";
    }
}
