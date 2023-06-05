package com.example.study.model.activeconfig;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liu
 * @date 2021/12/21 17:44
 * @description
 */
@Data
public class TaskPropertiesInfo implements Serializable {

    /**
     * 任务类型
     */
    private String type;
    /**
     * 任务编号,(自选基金->基金代码)
     */
    private String code;
    /**
     * 名称
     */
    private String name;
    /**
     * 分享Url
     */
    private String url;
    /**
     * 扩展信息
     */
    private String extendInfo;
    /**
     * 状态
     */
    @ApiModelProperty("状态")
    private int status = -2;



}
