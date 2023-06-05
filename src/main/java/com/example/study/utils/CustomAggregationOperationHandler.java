package com.example.study.utils;

import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperationContext;

/**
 * @Author: LongX
 * @Date: 2022/8/19 10:26
 * @Description: TODO
 * @Version: 1.0
 **/
public class CustomAggregationOperationHandler implements AggregationOperation {
    private String jsonOperation;

    public CustomAggregationOperationHandler(String jsonOperation) {
        this.jsonOperation = jsonOperation;
    }

    @Override
    public org.bson.Document toDocument(AggregationOperationContext aggregationOperationContext) {
        return aggregationOperationContext.getMappedObject(org.bson.Document.parse(jsonOperation));
    }
}
