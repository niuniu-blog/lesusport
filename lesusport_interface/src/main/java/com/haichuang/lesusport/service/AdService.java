package com.haichuang.lesusport.service;

import com.haichuang.lesusport.pojo.ad.Ad;

import java.util.List;

public interface AdService {
    /**
     * 加载广告详情
     */
    List<Ad> listAdByPositionId(Long id);

    /**
     * 返回广告显示的json串
     *
     * @param id
     * @return
     */
    String getAdsJson(Long id);
}
