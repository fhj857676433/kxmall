package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("kxmall_out_stock_spu")
public class OutStockSpuDO {
    //出库商品id
    private Integer id;

    //商品类目
    private String category;

    //商品条码
    @TableField("bar_code")
    private String barCode;

    //商品名称
    @TableField("spu_name")
    private String spuName;

    //商品规格id
    @TableField("sku_id")
    private Integer skuId;

    //商品名称
    @TableField("sku_name")
    private String skuName;

    //库存可用量
    @TableField("stock_num")
    private Long stockNum;

    //出库可用量
    @TableField("out_stock_num")
    private Long outStockNum;

    //商品单号
    @TableField("out_stock_numbers")
    private String outStockNumbers;
}
