package com.haichuang.lesusport.service.color;

import com.haichuang.lesusport.dao.product.ColorDao;
import com.haichuang.lesusport.pojo.product.Color;
import com.haichuang.lesusport.pojo.product.ColorQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("colorService")
@Transactional
public class ColorServiceImpl implements ColorService {
    @Autowired
    private ColorDao colorDao;

    @Override
    public List<Color> listColors() {
        ColorQuery colorQuery = new ColorQuery();
        ColorQuery.Criteria criteria = colorQuery.createCriteria();
        criteria.andParentIdNotEqualTo(0L);
        return colorDao.selectByExample(colorQuery);
    }
}
