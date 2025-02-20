package com.example.study.config.type;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

/**
 * @Author: LongX
 * @Date: 2025/2/19 9:10
 * @Description: ObjectTypeHandler
 * @Version: 1.0
 **/
@Slf4j
@MappedJdbcTypes(JdbcType.VARCHAR)
public abstract class ObjectTypeHandler<T> extends AbstractJsonTypeHandler<T> {

    @Override
    protected T parse(String json) {
        return JSON.parseObject(json, this.javaType());
    }

    @Override
    protected String toJson(T obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * 具体类型，由子类提供
     * @return 具体类型
     */
    protected abstract TypeReference<T> javaType();


}
