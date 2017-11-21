package com.haichuang.lesusport.service.message;


import com.haichuang.lesusport.pojo.product.Color;
import com.haichuang.lesusport.pojo.product.Product;
import com.haichuang.lesusport.pojo.product.Sku;
import com.haichuang.lesusport.service.CmsService;
import com.haichuang.lesusport.service.StaticPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.*;

/**
 * 静态化处理
 */
@Component("customerMessageListener")
public class CustomerMessageListener implements MessageListener {
    @Autowired
    private CmsService cmsService;
    @Autowired
    private StaticPageService staticPageService;

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            Long id = Long.parseLong(textMessage.getText());
            Map<String, Object> map = new LinkedHashMap<>();
            List<Sku> skus = cmsService.listSkuByProductId(id);
            Product product = cmsService.getProductById(id);
            map.put("product", product);
            map.put("skus", skus);
            Set<Color> colors = new HashSet<>();
            for (Sku sku : skus) {
                colors.add(sku.getColor());
            }
            map.put("colors", colors);
            staticPageService.index(map, id);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
