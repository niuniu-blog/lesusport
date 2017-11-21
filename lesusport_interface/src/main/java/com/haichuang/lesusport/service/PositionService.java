package com.haichuang.lesusport.service;

import com.haichuang.lesusport.pojo.ad.Position;

import java.util.List;

public interface PositionService {
    /**
     * 根据父id查询广告位置
     *
     * @param id
     * @return
     */
    List<Position> listPositionnByParentId(Long id);
}
