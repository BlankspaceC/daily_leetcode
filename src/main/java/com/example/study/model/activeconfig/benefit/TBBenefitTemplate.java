package com.example.study.model.activeconfig.benefit;

import java.math.BigDecimal;

/**
 * @Author: LongX
 * @Date: 2024/2/5 14:42
 * @Description: TBBenefitTemplate TODO
 * @Version: 1.0
 **/
public class TBBenefitTemplate {

    /**
     * 权益模板名称
     */
    private String templateName;

    /**
     * 0-活动 1-奖励中心
     */
    private Integer source;

    /**
     * 0-单权益 1-奖池
     */
    private Integer code;

    /**
     * 权益类型枚举值
     */
    private Integer type;

    /**
     * 0-草稿 1-待上线 2-已上线 3-已下线
     */
    private Integer status;

    /**
     * 奖池规则-json
     */
    private String benefitPoolRule;


    /**
     * 生成的权益实例面额最小值
     */
    private BigDecimal minValue;

    /**
     * 生成的权益实例面额最大值
     */
    private BigDecimal maxValue;

    /**
     * 策略中台策略id
     */
    private String sendStrategyId;

    /**
     * 最多能发放的权益实例数量
     */
    private Integer numLimit;

    /**
     * 操作账号
     */
    private String updater;
}
