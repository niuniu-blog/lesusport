package com.haichuang.lesusport.service;

import com.haichuang.lesusport.constans.Constans;
import com.haichuang.lesusport.service.login.SessionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service("sessionProvider")
public class SessionProviderImpl implements SessionProvider {
    @Autowired
    private Jedis jedis;
    //可以通过修改配置，修改存活时间
    private Integer expire = 30;

    public void setExpire(Integer expire) {
        this.expire = expire;
    }

    /**
     * 存放远程session
     *
     * @param key   cookie 生成的令牌
     * @param value 用户名
     */
    public void setAttributeForUsername(String key, String value) {
        jedis.set(key + ":" + Constans.USER_NAME, value);
        jedis.expire(key + ":" + Constans.USER_NAME, 60 * expire);
    }

    public String getAttributeForUsername(String key) {
        String username = jedis.get(key + ":" + Constans.USER_NAME);
        if (username != null) {
            jedis.expire(key + ":" + Constans.USER_NAME, 60 * expire);
            return username;
        }
        return null;
    }
}
