package com.kxmall.market.data.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.kxmall.market.data.domain.SuperDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kaixin
 * @version 1.0
 * @date 2020/2/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("kxmall_stock")
public class StockExcelDTO extends SuperDO {

    /**
     * 前置仓id
     */
    @TableField("storage_id")
    @Excel(name = "前置仓ID（必填）", orderNum = "1", width = 20)
    private Long storageId;

    /**
     * 前置仓id
     */
    @TableField("storage_id")
    //@Excel(name = "前置仓名称（必填）", orderNum = "2", width = 20)
    private String name;

    /**
     * 商品id
     */
    @TableField("spu_id")
    //@Excel(name = "商品ID（必填）", orderNum = "3", width = 15)
    private Long spuId;

    /**
     * 商品规格
     */
    //@Excel(name = "商品规格（必填）", orderNum = "4", width = 20)
    private String title;

    /**
     * 商品条码
     */
    @Excel(name = "商品条码（必填）", orderNum = "2", width = 20)
    private String barCode;

    /**
     * 销售状态
     */
    @Excel(name = "销售状态（必填:0 下架;1 在售）", orderNum = "3", width = 30)
    private Integer status;


    /**
     * 库存
     */
    @Excel(name = "可售量（必填）", orderNum = "4", width = 15)
    private Long stock;


    /**
     * 当前售价
     */
    @Excel(name = "当前售价（必填）", orderNum = "5", width = 20)
    private Integer price;

    /**
     * 错误信息
     */
    private String msg;

}
