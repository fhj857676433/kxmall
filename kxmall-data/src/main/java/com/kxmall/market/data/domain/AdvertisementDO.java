package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * Description:
 * User: admin
 * Date: 2019-07-08
 * Time: 下午6:50
 */
@Data
@TableName("kxmall_advertisement")
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementDO extends SuperDO {

    @TableField("ad_type")
    private Integer adType;

    private String title;

    private String url;

    @TableField("img_url")
    private String imgUrl;

    // 站外链接
    @TableField("out_url")
    private String outUrl;


    private Integer status;

    private String color;
}
