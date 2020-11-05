package com.kxmall.market.admin.api.api.dashboard;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.domain.OrderDO;
import com.kxmall.market.data.domain.SpuDO;
import com.kxmall.market.data.dto.DashboardIntegralDTO;
import com.kxmall.market.data.dto.SalesStatementDTO;
import com.kxmall.market.data.dto.SalesTopDTO;
import com.kxmall.market.data.dto.UserStatementDTO;
import com.kxmall.market.data.enums.OrderStatusType;
import com.kxmall.market.data.mapper.OrderMapper;
import com.kxmall.market.data.mapper.SpuMapper;
import com.kxmall.market.data.mapper.UserMapper;
import com.kxmall.market.data.model.KVModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by admin on 2019/7/15.
 */
@Service
@SuppressWarnings("all")
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private SpuMapper spuMapper;

    @Override
    public Object integral(Long adminId) throws ServiceException {
        DashboardIntegralDTO dto = new DashboardIntegralDTO();
        Integer orderWaitStock = orderMapper.selectCount(new EntityWrapper<OrderDO>().eq("status", OrderStatusType.WAIT_STOCK.getCode()));
        Integer spuCount = spuMapper.selectCount(new EntityWrapper<SpuDO>());
        List<KVModel<String, Long>> area = orderMapper.selectAreaStatistics();
        List<KVModel<String, Long>> channel = orderMapper.selectChannelStatistics();
        dto.setArea(area);
        dto.setChannel(channel);
        dto.setWaitStockCount(orderWaitStock);
        dto.setGoodsCount(spuCount);
        Integer days = 7;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String startDay = sdf.format(new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * days));
        List<KVModel<String, Long>> orderCountKVList = orderMapper.selectOrderCountStatistics(startDay);
        List<KVModel<String, Long>> orderSumKVList = orderMapper.selectOrderSumStatistics(startDay);
        SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
        List<Object[]> orderCount = new LinkedList<>();
        Object[] orderCountNameArray = new Object[days];
        Object[] orderCountValueArray = new Object[days];
        orderCount.add(orderCountNameArray);
        orderCount.add(orderCountValueArray);
        dto.setDaysOrder(orderCount);
        List<Object[]> orderSum = new LinkedList<>();
        Object[] orderSumNameArray = new Object[days];
        Object[] orderSumValueArray = new Object[days];
        orderSum.add(orderSumNameArray);
        orderSum.add(orderSumValueArray);
        dto.setDaysSum(orderSum);
        //这里是在补全 group by 为 0 的情况
        for (int i = 0; i < days; i++) {
            Date date = new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24 * i);
            String key = sdfDay.format(date);
            int i1 = orderCountKVList.indexOf(new KVModel<>(key, null));
            if (i1 >= 0) {
                orderCountNameArray[days - i - 1] = key;
                orderCountValueArray[days - i - 1] = orderCountKVList.get(i1).getValue();
            } else {
                orderCountNameArray[days - i - 1] = key;
                orderCountValueArray[days - i - 1] = 0;
            }

            int i2 = orderSumKVList.indexOf(new KVModel<>(key, null));
            if (i2 >= 0) {
                orderSumNameArray[days - i - 1] = key;
                orderSumValueArray[days - i - 1] = orderSumKVList.get(i2).getValue();
            } else {
                orderSumNameArray[days - i - 1] = key;
                orderSumValueArray[days - i - 1] = 0;
            }
        }
        return dto;
    }

    @Autowired
    private UserMapper userMapper;
    /**
     * 用户数量统计
     * @param adminId
     * @return
     * @throws ServiceException
     */

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<UserStatementDTO> countUser( Long adminId) throws ServiceException {
        List<UserStatementDTO> userStatements = new ArrayList<>();
        UserStatementDTO firstDay = new UserStatementDTO();
        UserStatementDTO secondDay = new UserStatementDTO();
        UserStatementDTO thirdDay = new UserStatementDTO();
        UserStatementDTO fourthDay = new UserStatementDTO();
        UserStatementDTO fifthDay = new UserStatementDTO();
        UserStatementDTO sixthDay = new UserStatementDTO();
        UserStatementDTO seventhDay = new UserStatementDTO();
        userStatements.add(firstDay);
        userStatements.add(secondDay);
        userStatements.add(thirdDay);
        userStatements.add(fourthDay);
        userStatements.add(fifthDay);
        userStatements.add(sixthDay);
        userStatements.add(seventhDay);
        UserStatementDTO userStatementDTO;
        for (int i = 0; i < 7; i++) {
            Date[] dates = getDate(i);
            userStatementDTO = userStatements.get(i);
            if(i == 0){
                userStatementDTO.setStatementDate("今日");
            }else if(i == 1){
                userStatementDTO.setStatementDate("昨日");
            }else{
                String format = simpleDateFormat.format(dates[0]);
                userStatementDTO.setStatementDate(format);
            }
            Long aLong = Long.valueOf(i + "");
            userStatementDTO.setId(aLong);
            Integer countTotalUser = userMapper.countTotalUser(dates[0]);
            userStatementDTO.setTotalUser(countTotalUser);
            Integer countNewUser = userMapper.countNewUser(dates[0],dates[1]);
            userStatementDTO.setNewUser(countNewUser);
            Integer countOnlineUser = userMapper.countOnlineUser(dates[0],dates[1]);
            userStatementDTO.setOnlineUser(countOnlineUser);
            Integer countOrderUser = userMapper.countOrderUser(dates[0],dates[1]);
            userStatementDTO.setOrderUser(countOrderUser);
            Integer countFirstOrderUser = userMapper.countFirstOrderUser(dates[0],dates[1]);
            userStatementDTO.setFirstOrderUser(countFirstOrderUser);
        }
        return userStatements;
    }

    @Override
    public List<SalesStatementDTO> getSalesStatement(Long storageId,Long adminId) throws ServiceException {

        List<SalesStatementDTO> salesCategoryRank = orderMapper.getSalesCategoryRank(storageId);
        for (SalesStatementDTO salesStatementDTO:salesCategoryRank) {
            Long categoryId = salesStatementDTO.getCategoryId();
            List<SalesTopDTO> salesCategoryRanTopFive = orderMapper.getSalesCategoryRanTopFive(categoryId, storageId);
            salesStatementDTO.setSalesTopDTOs(salesCategoryRanTopFive);
        }
        return salesCategoryRank;
    }

    @SuppressWarnings("all")
    public static Date[] getDate(int past){
        Date[] dates = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date start = calendar.getTime();
        dates[0] = start;
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date end = calendar.getTime();
        dates[1] = end;
        return dates;
    }

    public static Date[] getTwoHourDate(int past){
        Date[] dates = new Date[2];
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR));
        calendar.set(Calendar.HOUR_OF_DAY, (past - 1)*2);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date start = calendar.getTime();
        dates[0] = start;
        calendar.set(Calendar.HOUR_OF_DAY, past*2 - 1);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date end = calendar.getTime();
        dates[1] = end;
        return dates;
    }


    @Override
    public List<SalesStatementDTO> getTodayAndYesterdaySales(Long storageId, Long adminId) throws ServiceException {
        List<SalesStatementDTO> salesStatementDTOS = new ArrayList<>();
        SalesStatementDTO todaySales = orderMapper.getTodaySales(storageId);
        SalesStatementDTO yesterdaySales = orderMapper.getYesterdaySales(storageId);
        if(todaySales == null){
            todaySales = new SalesStatementDTO();
        }
        if(yesterdaySales == null){
            yesterdaySales = new SalesStatementDTO();
        }
        salesStatementDTOS.add(todaySales);
        salesStatementDTOS.add(yesterdaySales);
        return salesStatementDTOS;
    }


    @Override
    public List<SalesStatementDTO> getSalesByHour(Long storageId, Long adminId) throws ServiceException {
        int hour = getHour(new Date())/2 + 1 ;
        List<SalesStatementDTO> salesStatementDTOS = new ArrayList<>();
        for (int i = 1; i <= hour; i++) {
            Date[] twoHourDate = getTwoHourDate(i);
            SalesStatementDTO salesByHour = orderMapper.getSalesByHour(storageId, twoHourDate[0], twoHourDate[1]);
            salesByHour.setId(Long.valueOf(i+""));
            salesStatementDTOS.add(salesByHour);
        }
        return salesStatementDTOS;
    }

    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static void main(String[] args) {
        System.out.println(getHour(new Date())/2);
    }
}
