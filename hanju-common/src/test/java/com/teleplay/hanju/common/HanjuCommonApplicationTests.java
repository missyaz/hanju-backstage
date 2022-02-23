package com.teleplay.hanju.common;

import cn.hutool.core.date.DateTime;
import com.teleplay.hanju.common.entity.Index;
import com.teleplay.hanju.common.mapper.IndexMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class HanjuCommonApplicationTests {

    @Autowired(required = false)
    private IndexMapper indexMapper;

    @Test
    void testInsertUpdate() {
        Index index = new Index();
        index.setId(1491958158565666817L);
        index.setIsHot(true);
        indexMapper.insert(index);
        // indexMapper.updateById(index);
    }

}
