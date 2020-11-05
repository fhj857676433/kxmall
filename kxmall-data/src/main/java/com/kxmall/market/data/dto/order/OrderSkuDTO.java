package com.kxmall.market.data.dto.order;

import com.kxmall.market.data.domain.OrderDO;
import com.kxmall.market.data.domain.OrderSkuDO;
import com.kxmall.market.data.domain.StorageDO;
import lombok.Data;

import java.util.List;

/**
 * @description: 订单商品信息传输类
 * @author: fy
 * @date: 2020/03/13 12:05
 **/
@Data
public class OrderSkuDTO extends OrderDO {

    StorageDO storageDO;

    List<OrderSkuDO> orderSkuDoList;
}
