package com.haichuang.lesusport.controller;

import com.haichuang.lesusport.common.Utils.RequestUtils;
import com.haichuang.lesusport.pojo.buyer.BuyerCart;
import com.haichuang.lesusport.pojo.buyer.BuyerItem;
import com.haichuang.lesusport.pojo.order.Order;
import com.haichuang.lesusport.pojo.product.Sku;
import com.haichuang.lesusport.service.OrderService;
import com.haichuang.lesusport.service.login.BuyerService;
import com.haichuang.lesusport.service.login.SessionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("buyer")
public class BuyerController {
    @Autowired
    private SessionProvider sessionProvider;
    @Autowired
    private BuyerService buyerService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("submitOrder")
    public String submitOrder(Order order, HttpServletRequest request, HttpServletResponse response) {
        String username = sessionProvider.getAttributeForUsername(RequestUtils.getCSESSION(request, response));

        return null;
    }

    /**
     * 确认购买
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("trueBuy")
    public String trueBuy(HttpServletRequest request, HttpServletResponse response, Model model) {
        String username = sessionProvider.getAttributeForUsername(RequestUtils.getCSESSION(request, response));
        BuyerCart cart = buyerService.getBuyerCartFromRedis(username);
        List<BuyerItem> items = cart.getItems();
        if (items.size() > 0) {
            Boolean flag = false;
            for (BuyerItem item : items) {
                Sku skuBySkuId = buyerService.getSkuBySkuId(item.getSku().getId());
                if (skuBySkuId.getStock() < item.getAmount()) {
                    item.setHave(false);
                    flag = true;
                }
            }
            if (flag) {
                model.addAttribute("buyerCart", cart);
                return "cart";
            }
        } else {
            return "redirect:/shopping/tocart";
        }
        return "order";
    }
}
