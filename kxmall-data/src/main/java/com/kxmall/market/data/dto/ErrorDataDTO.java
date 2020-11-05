package com.kxmall.market.data.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
public class ErrorDataDTO extends SuperDO {

    /**
     * 前置仓id
     */
    @Excel(name = "前置仓ID", orderNum = "1", width = 15)
    private Long storageId;

    /**
     * 前置仓id
     */
    @Excel(name = "前置仓名称", orderNum = "2", width = 15)
    private String name;

    /**
     * 商品规格
     */
    //@Excel(name = "商品规格", orderNum = "3", width = 20)
    private String title;

    /**
     * 商品条码
     */
    @Excel(name = "商品条码", orderNum = "3", width = 15)
    private String barCode;

    /**
     * 错误信息
     */
    @Excel(name = "错误原因", orderNum = "4", width = 30)
    private String msg;


}
