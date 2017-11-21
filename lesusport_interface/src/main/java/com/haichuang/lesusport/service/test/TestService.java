package com.haichuang.lesusport.service.test;

import com.haichuang.lesusport.pojo.Test;

public interface TestService {
    /**
     * 测试dubbo
     *
     * @return
     */
    int getInt();

    /**
     * 整合测试,保存
     *
     * @param test
     */
    void saveTest(Test test);
}
