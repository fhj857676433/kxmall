package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *@author wxf
 *@date  2029/3/1 - 21:29
 * 入库DO
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("kxmall_enter_stock")
public class EnterStockDO extends SuperDO{

    /**
     * 前置仓ID
     */
    @TableField("storage_id")
    private Long storageId;
    /**
     * 入库状态 0 待入库;1 已入库
     */
    @TableField("enter_stock_status")
    private Integer enterStockStatus;

    /**
     * 更新人
     */
    @TableField("gmt_update_user_id")
    private Long gmtUpdateUserId;

    /**
     * 创建人
     */
    @TableField("gmt_create_user_id")
    private Long gmtCreateUserId;

    /**
     * 商品规格ID
     */
    @TableField("sku_id")
    private Long skuId;

    /**
     * 商品ID
     */
    @TableField("spu_id")
    private Long spuId;

    /**
     * 状态 0正常;1删除
     */
    @TableField("status")
    private Integer status;

    /**
     * 入库单号
     */
    @TableField("enter_stock_no")
    private String enterStockNo;

    /**
     * 入库量
     */
    @TableField("enter_num")
    private Long enterNum;

}
