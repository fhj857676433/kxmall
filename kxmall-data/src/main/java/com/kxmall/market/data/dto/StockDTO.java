package com.kxmall.market.data.dto;

import com.kxmall.market.data.dto.goods.SkuDTO;
import com.kxmall.market.data.domain.CategoryDO;
import com.kxmall.market.data.domain.SpuDO;
import com.kxmall.market.data.domain.StorageDO;
import com.kxmall.market.data.domain.SuperDO;
import lombok.Data;

import java.util.List;

/**
 * @author kaixin
 * @version 1.0
 * @date 2020/2/19
 */
@Data
public class StockDTO extends SuperDO{

    /**
     * 商品id
     */
    private Long spuId;

    /**
     * 规格id
     */
    private Long skuId;

    /**
     * 前置仓id
     */
    private Long storageId;

    /**
     * 现存库存量
     */
    private Long nowStock;

    /**
     * 库存
     */
    private Long stock;

    /**
     * 冻结库存
     */
    private Long frezzStock;

    /**
     * 当前售价
     */
    private Integer price;

    /**
     * 销量
     */
    private Long sales;

    /**
     *仓库名称
     */
    private StorageDO storageDO;

    /**
     *商品类目
     */
    private CategoryDO categoryDO;

    /**
     *商品类目列表
     */
    private List<CategoryDO> categoryDOs;

    /**
     * 商品名称
     */
    private SpuDO spuDO;


    /**
     * 商品条形码
     */
    private SkuDTO skuDTO;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 预警量
     */
    private Integer warningNum;

}
