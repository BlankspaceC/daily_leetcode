package com.example.study.model.activeconfig;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author liu
 * @date 2022/4/24 17:01
 * @description
 */

@Data
@NoArgsConstructor
public class TaskConfig {

    private String ID;

    /**
     * 活动Id
     */
    private String actionID;

    /**
     * 活动阶段
     */
    private String stageID;

    /**
     * 任务Id
     */
    private String taskID;

    /**
     * 任务类型
     */
    private int type;

    /**
     * 是否是系统任务 1:是 0:不是
     */
    private int isSysTask;

    /**
     * 来自任务库的任务信息,和isSysTask联动  1的时候properties有值
     */
    private String propertiesStr;

    /**
     * 任务简介
     */
    private String summary;

    /**
     * 排序顺序
     */
    private Integer sortOrder;

    /**
     * 是否任务间互斥
     */
    private int isMutex;

    /**
     * 活动期间
     */
    private int periods;

    /**
     * 任务次数
     */
    private Integer frequency;

    /**
     * 任务规则
     */
    private String accessRule;

    /**
     * 执行器规则
     */
    private String exeRule;

    /**
     * 执行器
     */
    private int runner;

    /**
     * 奖励规则
     */
    private String rewardRule;

    /**
     * 前端是否展示
     */
    private String isShow;

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
     * 来自任务库的任务信息,和isSysTask联动  1的时候properties有值
     */
    private TaskProperties properties;

    /**
     * 逻辑删除  1:删除 0:未删除
     */
    private Integer isDelete;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 源id-财富号后台对应任务id
     */
    private String resourceId;

    /**
     * 扩展字段
     */
    private String extendInfo;

}
