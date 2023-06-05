package com.example.study.model.activeconfig;

import lombok.Data;

/**
 * @author zhoufugui
 * @file : RewardExtend.java
 * @Software: IntelliJ IDEA
 * @date: 2022/7/1 16:08:39
 * @description: RewardExtend
 * @version: 1.0
 */
@Data
public class RewardExtend {

    /**
     * cfhId :
     * cfhName :
     * passportId :
     * imgUrl :
     * belongType : 1-自己 2-他人
     */
    /**
     * 财富号id
     */
    private String cfhId;
    /**
     * 财富号名称
     */
    private String cfhName;
    /**
     * 机构通行证id
     */
    private String passportId;
    /**
     * 奖品图片
     */
    private String imgUrl;
    /**
     * 奖品归属 1-自己 2-他人
     */
    private int belongType;
}
