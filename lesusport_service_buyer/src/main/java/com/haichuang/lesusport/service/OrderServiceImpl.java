package com.haichuang.lesusport.service;

import com.haichuang.lesusport.dao.order.OrderDao;
import com.haichuang.lesusport.pojo.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private Jedis jedis;

    public void saveOrder(Order order, String username) {
        order.setId(jedis.incr("oid"));
        //TODO 未完成
    }
}
