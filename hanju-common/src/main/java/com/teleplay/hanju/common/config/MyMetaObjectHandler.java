package com.teleplay.hanju.common.config;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @author f
 * @desc
 * @create 2022-02-11 9:45
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert .....");
        this.strictInsertFill(metaObject,"crateTime", DateTime.class,DateTime.now());
        this.strictInsertFill(metaObject,"updateTime", DateTime.class,DateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update .....");
        this.strictUpdateFill(metaObject,"updateTime", DateTime.class,DateTime.now());
    }
}
