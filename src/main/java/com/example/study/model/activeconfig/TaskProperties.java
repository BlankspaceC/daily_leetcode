package com.example.study.model.activeconfig;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liu
 * @date 2021/12/21 17:41
 * @description
 */
@Data
public class TaskProperties implements Serializable {
    /*
    * Serial：'11111111',Res：[{Type:'1',Code：'000001',Name：'华夏大盘',Url:'',Extend:''}]}*/

    /**
     * 来自任务库的任务编号
     */
    private String serial;


    private List<TaskPropertiesInfo> res;


}
