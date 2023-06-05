package com.example.study.model.activeconfig;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author liu
 * @date 2021/12/21 13:57
 * @description 活动信息配置
 */

@Data
@NoArgsConstructor
public class ActiveConfig implements Serializable {

    private String ID;

    /**
     * 活动Id
     */
    private String actionID;
    /**
     * 活动状态
     */
    private int state;
    /**
     * 活动开始时间
     */
    private Date startTime;
    /**
     * 活动结束时间
     */
    private Date endTime;
    /**
     * 活动简介
     */
    private String summary;

    /**
     * 来源
     */
    private String source;

    private String extend;

    /**
     * 活动创建时间
     */
    private Date createTime;
    /**
     * 活动创建者
     */
    private String creater;
    /**
     * 活动更新时间
     */
    private Date updateTime;
    /**
     * 活动更新者
     */
    private String updater;
    /**
     * 活动阶段信息
     */
    private List<StagesConfig> stages;

    /**
     * 逻辑删除  1:删除 0:未删除
     */
    private Integer isDelete;


}
