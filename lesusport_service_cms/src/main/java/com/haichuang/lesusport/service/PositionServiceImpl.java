package com.haichuang.lesusport.service;

import com.haichuang.lesusport.dao.ad.PositionDao;
import com.haichuang.lesusport.pojo.ad.Position;
import com.haichuang.lesusport.pojo.ad.PositionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("positionService")
public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionDao positionDao;

    @Override
    public List<Position> listPositionnByParentId(Long id) {
        PositionQuery positionQuery = new PositionQuery();
        positionQuery.createCriteria().andParentIdEqualTo(id);
        return positionDao.selectByExample(positionQuery);
    }
}
