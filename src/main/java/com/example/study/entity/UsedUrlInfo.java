package com.example.study.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: LongX
 * @Date: 2022/8/26 9:33
 * @Description: TODO
 * @Version: 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsedUrlInfo {
    private String usedUrlId;
    private String pid;
    private String url;
}
