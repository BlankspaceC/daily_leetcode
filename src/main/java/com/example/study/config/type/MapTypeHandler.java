package com.example.study.config.type;

import com.alibaba.fastjson2.TypeReference;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.util.Map;

/**
 * @Author: LongX
 * @Date: 2025/2/19 9:10
 * @Description: MapTypeHandler
 * @Version: 1.0
 **/
@MappedTypes({Map.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class MapTypeHandler extends ObjectTypeHandler<Map<String, Object>> {
    @Override
    protected TypeReference<Map<String, Object>> javaType() {
        return new TypeReference<Map<String, Object>>() {};
    }
}
