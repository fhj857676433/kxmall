package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 *
 * @author admin
 * @date 2019/7/2
 */
@Data
@TableName("kxmall_sku")
public class SkuDO extends SuperDO {

    @TableField("spu_id")
    private Long spuId;

    @TableField("bar_code")
    private String barCode;

    /**
     * SKU显示文字
     */
    private String title;

    private String img;

}
