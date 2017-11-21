package com.haichuang.lesusport.service;

import com.haichuang.lesusport.common.Utils.JsonUtils;
import com.haichuang.lesusport.dao.ad.AdDao;
import com.haichuang.lesusport.dao.ad.PositionDao;
import com.haichuang.lesusport.pojo.ad.Ad;
import com.haichuang.lesusport.pojo.ad.AdQuery;
import com.haichuang.lesusport.pojo.ad.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service("adService")
public class AdServiceImpl implements AdService {
    @Autowired
    private AdDao adDao;
    @Autowired
    private PositionDao positionDao;
    @Autowired
    private Jedis jedis;

    @Override
    public List<Ad> listAdByPositionId(Long id) {
        AdQuery adQuery = new AdQuery();
        adQuery.createCriteria().andPositionIdEqualTo(id);
        List<Ad> ads = adDao.selectByExample(adQuery);
        Position position = positionDao.selectByPrimaryKey(id);
        for (Ad ad : ads) {
            ad.setPositionName(position.getName());
        }
        return ads;
    }

    public String getAdsJson(Long id) {
        String ads = jedis.get("adJson");
        if (null == ads) {
            AdQuery adQuery = new AdQuery();
            adQuery.createCriteria().andPositionIdEqualTo(id);
            ads = JsonUtils.ObjectToJson(adDao.selectByExample(adQuery));
            jedis.set("adJson", ads);
            jedis.expire("adJson", 60 * 60 * 24);
        }
        return ads;
    }
}
