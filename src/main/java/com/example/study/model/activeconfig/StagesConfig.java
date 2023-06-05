package com.example.study.model.activeconfig;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author liu
 * @date 2021/12/21 14:04
 * @description 活动阶段信息
 */

@Data
@NoArgsConstructor
public class StagesConfig implements Serializable {

    private String ID;
    /**
     * 活动阶段Id
     */
    private String stageID;
    /**
     * 活动Id
     */
    private String actionID;
    /**
     * 该阶段活动开始时间
     */
    private Date startTime;
    /**
     * 该阶段活动结束时间
     */
    private Date endTime;
    /**
     * 该阶段活动简介
     */
    private String summary;
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
     * 该阶段活动阶段信息
     */
    private List<TaskConfig> tasks;


    /**
     * 逻辑删除  1:删除 0:未删除
     */
    private Integer isDelete;


}
