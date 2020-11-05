package com.kxmall.market.admin.api.api.enterstock;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.kxmall.market.admin.api.api.stock.StockServiceImpl;
import com.kxmall.market.core.exception.AdminServiceException;
import com.kxmall.market.core.exception.ExceptionDefinition;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.domain.AdminDO;
import com.kxmall.market.data.domain.EnterStockDO;
import com.kxmall.market.data.domain.StockDO;
import com.kxmall.market.data.dto.EnterStockDTO;
import com.kxmall.market.data.mapper.AdminMapper;
import com.kxmall.market.data.mapper.EnterStockMapper;
import com.kxmall.market.data.mapper.StockMapper;
import com.kxmall.market.data.model.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 仓库接口服务
 *
 * @author wxf
 * @date 2020/3/1
 */
@Service
@SuppressWarnings("all")
public class EnterStockServiceImpl implements EnterStockService {

    @Autowired
    private EnterStockMapper enterStockMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockServiceImpl stockService;

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Page<EnterStockDTO> list(Long storageId, Integer enterStockStatus, String enterStockNo, Long gmtCreate, Integer page, Integer limit, Long adminId) throws ServiceException {
        Wrapper<EnterStockDO> wrapper = new EntityWrapper<>();
        wrapper.eq("status", 0);
        if (storageId != null) {
            wrapper.eq("storage_id", storageId);
        }
        if (enterStockStatus != null) {
            wrapper.eq("enter_stock_status", enterStockStatus);
        }
        if (!StringUtils.isEmpty(enterStockNo)) {
            wrapper.like("enter_stock_no", enterStockNo);
        }
        // 查询当天
        if (!StringUtils.isEmpty(gmtCreate)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(gmtCreate));
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date start = calendar.getTime();
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            Date end = calendar.getTime();
            wrapper.between("gmt_create", start,end);
        }
        List<EnterStockDO> enterStockDOS = enterStockMapper.selectPage(new RowBounds((page - 1) * limit, limit), wrapper);
        Integer count = enterStockMapper.selectCount(wrapper);
        List<EnterStockDTO> enterStockDTOS = new ArrayList<>();
        for (EnterStockDO enterStock:enterStockDOS) {
            EnterStockDTO enterStockDTO = new EnterStockDTO();
            BeanUtils.copyProperties(enterStock,enterStockDTO);
            Long gmtCreateUserId = enterStock.getGmtCreateUserId();
            Long gmtUpdateUserId = enterStock.getGmtUpdateUserId();
            AdminDO gmtCreateUser = adminMapper.selectById(gmtCreateUserId);
            AdminDO gmtUpdateUser = adminMapper.selectById(gmtUpdateUserId);
            enterStockDTO.setGmtCreateUser(gmtCreateUser);
            enterStockDTO.setGmtUpdateUser(gmtUpdateUser);
            enterStockDTOS.add(enterStockDTO);
        }
        return new Page<>(enterStockDTOS, page, limit, count);
    }

    @Override
    @Transactional
    public String create(String json, Long adminId) throws ServiceException {
        List<EnterStockDO> enterStockDOs = JSONArray.parseArray(json,EnterStockDO.class);
        for (EnterStockDO enterStockDO:enterStockDOs) {
            Date now = new Date();
            enterStockDO.setGmtUpdate(now);
            enterStockDO.setGmtCreate(now);
            enterStockDO.setGmtUpdateUserId(adminId);
            enterStockDO.setGmtCreateUserId(adminId);
            enterStockDO.setEnterStockNo(createEnterStockNo());
            enterStockMapper.insert(enterStockDO);
        }
        return "ok";

       /* if(enterStockDO.getEnterStockStatus() == 1){
            //判断库存中是否有改记录
            StockDO stockDO = new StockDO();
            stockDO.setStorageId(enterStockDO.getStorageId());
            stockDO.setSkuId(enterStockDO.getSkuId());
            StockDO stock = stockMapper.selectOne(stockDO);
            if(stock == null){
                //无，添加新纪录
                stockService.create(enterStockDO.getSpuId(),enterStockDO.getSkuId(),enterStockDO.getStorageId(),0,enterStockDO.getEnterNum(),0L,0L,0,adminId);
            }else{
                //有，添加库存
                Long stockNum = stock.getStock();
                stockNum += enterStockDO.getEnterNum();
                stock.setStock(stockNum);
                Integer id = stockMapper.update(stock, new EntityWrapper<StockDO>().eq("id", stock.getId()));
            }
        }*/
    }

    @Override
    @Transactional
    public EnterStockDO UpdateEnterStockStatus(Long id, Integer enterStockStatus, Long adminId) throws ServiceException {
        EnterStockDO enterStockDO = enterStockMapper.selectById(id);
        if(enterStockStatus == 1){
            //判断库存中是否有改记录
            StockDO stockDO = new StockDO();
            stockDO.setStorageId(enterStockDO.getStorageId());
            stockDO.setSkuId(enterStockDO.getSkuId());
            StockDO stock = stockMapper.selectOne(stockDO);
            /*if(stock == null){
                //无，添加新纪录
                stockService.create(enterStockDO.getSpuId(),enterStockDO.getSkuId(),enterStockDO.getStorageId(),0,enterStockDO.getEnterNum(),0L,0L,0,adminId);
            }else{*/
                //有，添加库存
                Long stockNum = stock.getStock();
                stockNum += enterStockDO.getEnterNum();
                stock.setStock(stockNum);
                stockMapper.update(stock, new EntityWrapper<StockDO>().eq("id", stock.getId()));
            //}
        }
        enterStockDO.setEnterStockStatus(enterStockStatus);
        if (enterStockMapper.updateById(enterStockDO) > 0) {
            return enterStockDO;
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    @Transactional
    public String delete(Long id, Long adminId) throws ServiceException {
        EnterStockDO enterStockDO = enterStockMapper.selectById(id);
        enterStockDO.setStatus(1);
        if (enterStockMapper.updateById(enterStockDO) > 0) {
            return "ok";
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    @Transactional
    public EnterStockDO update(EnterStockDO enterStockDO, Long adminId) throws ServiceException {
        enterStockDO.setGmtUpdate(new Date());
        enterStockDO.setGmtUpdateUserId(adminId);
        if (enterStockMapper.updateById(enterStockDO) > 0) {
            return enterStockDO;
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    public EnterStockDO selectById(Long id, Long adminId) {
        return enterStockMapper.selectById(id);
    }

    @Override
    @Transactional
    public String deleteBatch(Long[] ids, Long adminId) throws ServiceException {
        try {
            for (Long id:ids) {
                EnterStockDO enterStockDO = enterStockMapper.selectById(id);
                enterStockDO.setStatus(1);
                enterStockMapper.updateById(enterStockDO);
            }
            return "ok";
        }catch (Exception e){
            throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
        }
    }

    @Override
    public Map<String, Object> getGoodsDetail(Long id, Long adminId) throws ServiceException {
        return enterStockMapper.getGoodsDetail(id);
    }

    private synchronized String createEnterStockNo() {
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyyMMddHHmmssS");
        String timeStr=format.format(date);
        int random = (int) (Math.random() * 1000000);
        return timeStr+random;
    }
}
