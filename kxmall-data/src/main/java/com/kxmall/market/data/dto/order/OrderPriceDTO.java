package com.kxmall.market.data.dto.order;

import com.kxmall.market.data.dto.goods.SkuDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @description: 订单创建时价格的传输实体类
 * @author: fy
 * @date: 2020/03/13 13:39
 **/
@Data
public class OrderPriceDTO implements Serializable {

    private Integer skuOriginalTotalPrice;

    /*** 商品总价*/
    private Integer skuTotalPrice;

    /*** 运费*/
    private Integer freightPrice;

    /*** 优惠券*/
    private Integer couponPrice;

    /*** 优惠券ID*/
    private Long couponId;

    /*** 团购*/
    private Long groupShopId;

    /*** 计算优惠后，实际需要支付的价格*/
    private Integer actualPrice;

    /*** 支付加个*/
    private Integer payPrice;

    /***每个地方仓库的价格不一样,各自商户设置的价格 skuId 对应仓库的价格 */
    Map<Long, SkuDTO> skuIdDTOMap;
}
