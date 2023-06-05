package com.example.study.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author LongX
 * @since 2022-08-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("url_info")
public class UrlInfoEntity extends Model<UrlInfoEntity> {

    private static final long serialVersionUID = 1L;

    private String id;

    private String url;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
