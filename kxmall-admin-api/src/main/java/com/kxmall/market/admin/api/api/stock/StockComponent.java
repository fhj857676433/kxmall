package com.kxmall.market.admin.api.api.stock;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kxmall.market.data.component.CacheComponent;
import com.kxmall.market.data.component.LockComponent;
import com.kxmall.market.data.domain.StockDO;
import com.kxmall.market.data.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author xiebo
 */
@Component
public class StockComponent {

    private static StockComponent stockComponent;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private LockComponent lockComponent;

    public static final String STOCK_LEFT = "stock_left"; //有效值

    public static final String SKU_ = "sku_"; //有效值

    @PostConstruct
    public void init() {
        stockComponent = this;
        List<StockDO> stockDOList = stockComponent.stockMapper.selectList(new EntityWrapper<StockDO>().eq("status", 1));
        stockComponent.cacheComponent.putObj(STOCK_LEFT, stockDOList, null);
    }

    /**
     * 下单调用 stock -= 1, freezestock += 1
     * 原方法要加事务
     */
//    public void takeOrder(StockDO stock){
//        if (lockComponent.tryLock(SKU_ + stock.getSkuId(), 300)) {
//            stock.setStock(stock.getStock() - 1);
//            stock.setFrezzStock(stock.getFrezzStock() + 1);
//        }
//    }
//
//    /**
//     * 支付调用 freezestock -= 1
//     * @param stock
//     */
//    public void payOrder(StockDO stock) {
//        if (lockComponent.tryLock(SKU_ + stock.getSkuId(), 300)) {
//            stock.setFrezzStock(stock.getFrezzStock() - 1);
//        }
//    }
//
//    /**
//     * 验货后新增库存
//     * @param stock
//     */
//    public void refund(StockDO stock) {
//        if (lockComponent.tryLock(SKU_ + stock.getSkuId(), 300)) {
//            stock.setStock(stock.getStock() - 1);
//        }
//    }
}
