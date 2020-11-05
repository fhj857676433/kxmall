package com.kxmall.market.data.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.kxmall.market.data.dto.SalesStatementDTO;
import com.kxmall.market.data.dto.SalesTopDTO;
import com.kxmall.market.data.dto.order.OrderDTO;
import com.kxmall.market.data.model.KVModel;
import com.kxmall.market.data.domain.OrderDO;
import com.kxmall.market.data.domain.SubOrderDO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by admin on 2019/7/6.
 */
public interface OrderMapper extends BaseMapper<OrderDO> {

    public List<OrderDTO> selectOrderPage(@Param("status") Integer status, @Param("offset") Integer offset, @Param("limit") Integer limit, @Param("userId") Long userId);

    public List<OrderDTO> selectOrderPages(@Param("status") List<Integer> status, @Param("offset") Integer offset, @Param("limit") Integer limit, @Param("userId") Long userId);


    public Long countOrder(@Param("status") Integer status, @Param("offset") Integer offset, @Param("limit") Integer limit, @Param("userId") Long userId);

    public Long countOrders(@Param("status") List<Integer> status, @Param("offset") Integer offset, @Param("limit") Integer limit, @Param("userId") Long userId);

//    /**
//     * 获取订单信息，SKU信息，仓库信息
//     *
//     * @param orderNo 订单编号
//     * @return 订单信息集
//     */
//    public OrderSkuDTO selectOrderAndSkuAndStorageInfo(@Param("orderNo") String orderNo);

    /**
     * 获取订单地区统计
     *
     * @return
     */
    public List<KVModel<String, Long>> selectAreaStatistics();

    public List<KVModel<String, Long>> selectChannelStatistics();

    public List<KVModel<String, Long>> selectOrderCountStatistics(String gmtStart);

    public List<KVModel<String, Long>> selectOrderSumStatistics(String gmtStart);

    public List<String> selectExpireOrderNos(@Param("status") Integer status, @Param("time") Date time);

    public List<SubOrderDO> listByStatus(SubOrderDO subOrderDO);

    /**
     * 获取一级类目销量排名
     *
     * @return
     */
    List<SalesStatementDTO> getSalesCategoryRank(@Param("storageId") Long storageId);

    /**
     * 获取商品销量排名
     *
     * @return
     */
    List<SalesTopDTO> getSalesCategoryRanTopFive(@Param("categoryId") Long categoryId, @Param("storageId") Long storageId);

    /**
     * 今日销量
     *
     * @return
     */
    SalesStatementDTO getTodaySales(@Param("storageId") Long storageId);

    /**
     * 昨日销量
     *
     * @return
     */
    SalesStatementDTO getYesterdaySales(@Param("storageId") Long storageId);

    /**
     * 按两小时统计销量
     *
     * @return
     */
    SalesStatementDTO getSalesByHour(@Param("storageId") Long storageId, @Param("start") Date start, @Param("end") Date end);


}
