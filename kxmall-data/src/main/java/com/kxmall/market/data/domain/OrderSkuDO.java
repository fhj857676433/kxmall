package com.kxmall.market.data.domain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * Created by admin on 2019/7/5.
 */
@Data
@TableName("kxmall_order_sku")
public class OrderSkuDO extends SuperDO {

    @TableField("sku_id")
    private Long skuId;

    /**
     * 冗余SPUID方便评论
     */
    @TableField("spu_id")
    private Long spuId;

    @TableField("order_id")
    private Long orderId;

    /**
     * 冗余，方便技术查库
     */
    @TableField("order_no")
    @Excel(name = "订单号", orderNum = "1", width = 20)
    private String orderNo;

    /**
     * SPU 标题
     */
    @TableField("spu_title")
    @Excel(name = "商品名", orderNum = "2", width = 20)
    private String spuTitle;

    /**
     * SKU 标题， 即小规格名称
     */
    private String title;

    @TableField("bar_code")
    private String barCode;

    @Excel(name = "商品数量", orderNum = "3", width = 20)
    @TableField("num")
    private Integer num;

    @TableField("original_price")
    private Integer originalPrice;

    /**
     * 单价
     */
    @Excel(name = "单价", orderNum = "4", width = 20)
    @TableField("price")
    private Integer price;

    /**
     * SKU 或 SPU主图 （优先使用SKU图）
     */
    private String img;

    //单位
    private String unit;

    /**
     * 可售量
     */
    @TableField(exist = false)
    private Long salableNum;

    /**
     * 分类
     */
    @TableField(exist = false)
    private String category;

}
