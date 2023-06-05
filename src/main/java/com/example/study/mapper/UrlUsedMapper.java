package com.example.study.mapper;

import com.example.study.entity.UrlUsedEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.study.entity.UsedUrlInfo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LongX
 * @since 2022-08-26
 */
public interface UrlUsedMapper extends BaseMapper<UrlUsedEntity> {
    public List<UsedUrlInfo> selectUsedUrl(String beginUrlId);
}
