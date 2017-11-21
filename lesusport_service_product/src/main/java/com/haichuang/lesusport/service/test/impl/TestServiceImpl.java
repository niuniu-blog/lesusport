package com.haichuang.lesusport.service.test.impl;

import com.haichuang.lesusport.dao.TestDao;
import com.haichuang.lesusport.pojo.Test;
import com.haichuang.lesusport.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("testService")
@Transactional
public class TestServiceImpl implements TestService {
    @Autowired
    private TestDao testDao;

    @Override
    public void saveTest(Test test) {
        testDao.saveTest(test);
//        throw new RuntimeException();
    }

    @Override
    public int getInt() {
        return 1;
    }
}
