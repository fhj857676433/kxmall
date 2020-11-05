package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("kxmall_goods_out_stock")
public class GoodsOutStockDO {

    //出库id
    private Long id;

    //仓库id
    private Long storageId;

    //出库单号
    private String outStockNumbers;

    //出库状态
    private Integer states;

    //出库人
    private String outgoingPerson;

    //出库时间
    private Date outgoingTime;

    //更新人
    private String updatePerson;

    //更新创建时间
    private Date gmtUpdate;

    //备注
    private String remarks;
}
