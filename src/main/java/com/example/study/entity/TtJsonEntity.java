package com.example.study.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.Map;

import com.example.study.config.type.MapTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author LongX
 * @since 2025-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName(value = "tt_json",autoResultMap = true)
public class TtJsonEntity extends Model<TtJsonEntity> {

    private static final long serialVersionUID = 1L;

    private String id;

    @TableField(value = "`config`", typeHandler = MapTypeHandler.class)
    private Map<String,Object> config;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
