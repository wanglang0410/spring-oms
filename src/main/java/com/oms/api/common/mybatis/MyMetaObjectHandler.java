package com.oms.api.common.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入时的填充策略
     *
     * @param metaObject meta参数
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createdAt", Long.class, new Date().getTime() / 1000);
        this.strictInsertFill(metaObject, "updatedAt", Long.class, new Date().getTime() / 1000);
    }

    /**
     * 更新时的填充策略
     *
     * @param metaObject  meta参数
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 解决不会覆盖原值的情况
        metaObject.setValue("updatedAt", null);
        this.strictInsertFill(metaObject, "updatedAt", Long.class, new Date().getTime() / 1000);
    }
}
