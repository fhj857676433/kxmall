package com.kxmall.market.admin.api.api.goodsoutstock;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import com.kxmall.market.core.exception.AdminServiceException;
import com.kxmall.market.core.exception.ExceptionDefinition;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.domain.GoodsOutStockDO;
import com.kxmall.market.data.domain.OutStockSpuDO;
import com.kxmall.market.data.domain.StorageDO;
import com.kxmall.market.data.dto.GoodsOutStockDTO;
import com.kxmall.market.data.enums.GoodsOutStockType;
import com.kxmall.market.data.enums.StorageStatusType;
import com.kxmall.market.data.mapper.GoodsOutStockMapper;
import com.kxmall.market.data.mapper.OutStockSpuMapper;
import com.kxmall.market.data.mapper.StockMapper;
import com.kxmall.market.data.mapper.StorageMapper;
import com.kxmall.market.data.model.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class GoodsOutStockServiceImpl implements GoodsOutStockService{

    @Autowired
    private GoodsOutStockMapper goodsOutStockMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private OutStockSpuMapper outStockSpuMapper;

    @Autowired
    private StorageMapper storageMapper;


    @Override
    public Page<GoodsOutStockDO> list(Long storageId, String outStockNumbers, Integer states, String outgoingDay, Integer page, Integer limit, Long adminId) throws ServiceException {
        Wrapper<GoodsOutStockDO> wrapper =new EntityWrapper<>();
        if(!StringUtils.isEmpty(storageId)){
            wrapper.eq("storageId",storageId);
        }
        if(outStockNumbers!=null){
            wrapper.like("outStockNumbers",outStockNumbers);
        }
        if(states!=null){
            wrapper.eq("states",states);
        }
        if(!StringUtils.isEmpty(outgoingDay)){
            wrapper.like("outgoingTime",outgoingDay);
        }
        List<GoodsOutStockDO> goodsOutStockDOS =goodsOutStockMapper.selectPage(new RowBounds((page-1)*limit,limit),wrapper);
        Integer count =goodsOutStockMapper.selectCount(wrapper);
        return new Page<>(goodsOutStockDOS,page,limit,count);
    }

    @Override
    public GoodsOutStockDTO selectById(String OutStockNumbers,Long id, Long adminId) throws ServiceException {
        Wrapper<OutStockSpuDO> wrapper =new EntityWrapper<>();
        wrapper.like("out_stock_numbers",OutStockNumbers);
        List<OutStockSpuDO> outStockSpuDOS =outStockSpuMapper.selectList(wrapper);
        GoodsOutStockDO goodsOutStockDO =goodsOutStockMapper.selectById(id);
        GoodsOutStockDTO goodsOutStockDTO =new GoodsOutStockDTO();
        goodsOutStockDTO.setOutStockSpuDOS(outStockSpuDOS);
        BeanUtils.copyProperties(goodsOutStockDO,goodsOutStockDTO);
        return goodsOutStockDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GoodsOutStockDTO create(GoodsOutStockDTO goodsOutStockDTO, Long adminId) throws ServiceException {
        //自动生成出库单号,O+年月日+流水号
        //查询数据库最新生成的编号
        GoodsOutStockDO goodsOutStockDO1 =goodsOutStockMapper.selectByMax();
        String max_code ="";//定义数据库的截取的数据
        String out_skock ="";//定义拼接好的字符串
        if(goodsOutStockDO1!=null){
            max_code =goodsOutStockDO1.getOutStockNumbers();
        }
        //定义时间字符串拼接
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMdd");
        String uid_pfix =simpleDateFormat.format(new Date());
        //判断数据库是否有数据
        if(max_code != null && max_code.contains(uid_pfix)){
            String uid_end =max_code.substring(9,14);
            Integer endNum =Integer.parseInt(uid_end);
            //100001
            endNum = 100000+endNum+1;
            String num =endNum+"";
            //去掉100001中的首位1
            String numm =num.substring(1);
            out_skock ="O"+uid_pfix+numm;
        }else {
            //数据库没数据时
            out_skock ="O"+uid_pfix+"00001";
        }
        //出库商品加入数据库
        List<OutStockSpuDO> outStockSpuDOS =goodsOutStockDTO.getOutStockSpuDOS();
        if(!CollectionUtils.isEmpty(outStockSpuDOS)){
            for(OutStockSpuDO outStockSpuDO :outStockSpuDOS){
                outStockSpuDO.setOutStockNumbers(out_skock);
                if(outStockSpuMapper.insert(outStockSpuDO)<=0){
                    throw new AdminServiceException(ExceptionDefinition.GOODS_OUT_INSERT);
                }
            }
        }
        //出库添加
        GoodsOutStockDO goodsOutStockDO =new GoodsOutStockDO();
        BeanUtils.copyProperties(goodsOutStockDTO,goodsOutStockDO);
        goodsOutStockDO.setOutStockNumbers(out_skock);
        goodsOutStockDO.setStates(GoodsOutStockType.TO_BE_FOR_STOCK.getCode());
        goodsOutStockDO.setGmtUpdate(new Date());
        if(goodsOutStockMapper.insert(goodsOutStockDO)<=0){
            throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
        }
        return goodsOutStockDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public GoodsOutStockDTO update(GoodsOutStockDTO goodsOutStockDTO, Long adminId) throws ServiceException {
        Wrapper<OutStockSpuDO> wrapper =new EntityWrapper<>();
        wrapper.like("out_stock_numbers",goodsOutStockDTO.getOutStockNumbers());
        if(outStockSpuMapper.delete(wrapper) <= 0){
            throw new AdminServiceException(ExceptionDefinition.GOODS_OUT_Delete);
        }
        //出库商品加入数据库
        List<OutStockSpuDO> outStockSpuDOS =goodsOutStockDTO.getOutStockSpuDOS();
        if(!CollectionUtils.isEmpty(outStockSpuDOS)){
            for(OutStockSpuDO outStockSpuDO :outStockSpuDOS){
                if(outStockSpuMapper.insert(outStockSpuDO)<=0){
                    throw new AdminServiceException(ExceptionDefinition.GOODS_OUT_INSERT);
                }
            }
        }
        GoodsOutStockDO goodsOutStockDO =new GoodsOutStockDO();
        BeanUtils.copyProperties(goodsOutStockDTO,goodsOutStockDO);
        goodsOutStockDO.setGmtUpdate(new Date());
        System.out.println(goodsOutStockDO);
        if(goodsOutStockMapper.updateById(goodsOutStockDO)>0){
            return goodsOutStockDTO;
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateOutOfStock(String outgoingPerson,Long adminId, Long storageId, String outStockNumbers) throws ServiceException {
        if (StringUtils.isEmpty(storageId) && StringUtils.isEmpty(StringUtils.isEmpty(outStockNumbers))) {
            throw new AdminServiceException(ExceptionDefinition.GOODS_ID_NOT);
        }
        //根据出库的商品数量更新仓库的数量
        Wrapper<OutStockSpuDO> wrapper =new EntityWrapper<>();
        wrapper.like("out_stock_numbers",outStockNumbers);
        List<OutStockSpuDO> outStockSpuDOS =outStockSpuMapper.selectList(wrapper);
        Long outStockNum;//出库数量
        for (OutStockSpuDO outStockSpuDO : outStockSpuDOS){
            outStockNum =outStockSpuDO.getOutStockNum();
            Integer skuId =outStockSpuDO.getSkuId();
            if(stockMapper.updateSock(storageId,skuId,outStockNum)<=0){
                throw new AdminServiceException(ExceptionDefinition.GOODS_NOT_STOCK);
            }
        }
        //更新出库状态
        GoodsOutStockDO goodsOutStockDO =new GoodsOutStockDO();
        goodsOutStockDO.setStates(GoodsOutStockType.OUT_FOR_STOCK.getCode());
        goodsOutStockDO.setOutgoingPerson(outgoingPerson);
        goodsOutStockDO.setOutgoingTime(new Date());
        goodsOutStockDO.setGmtUpdate(new Date());
        Wrapper<GoodsOutStockDO> wrapper1 =new EntityWrapper<>();
        wrapper1.like("outStockNumbers",outStockNumbers);
        if(goodsOutStockMapper.update(goodsOutStockDO,wrapper1)<=0){
            throw new AdminServiceException(ExceptionDefinition.GOODS_STOCK_FALSE);
        }
        return "ok";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String delete(Long adminId, Long id, String outStockNumbers) throws ServiceException {
        //删除出库信息
        if(goodsOutStockMapper.deleteById(id)<=0){
            throw new AdminServiceException(ExceptionDefinition.GOODS_DELETE);
        }
        //批量删除出库商品
        Wrapper<OutStockSpuDO> wrapper =new EntityWrapper<>();
        wrapper.like("out_stock_numbers",outStockNumbers);
        if(outStockSpuMapper.delete(wrapper)<=0){
            throw new AdminServiceException(ExceptionDefinition.GOODS_OUT_SPU_DELETE);
        }
        return "ok";
    }


    @Override
    public List<StorageDO> storagAllName(Long adminId) throws ServiceException {
        int state =StorageStatusType.NOMRAL.getCode();
        List<StorageDO> storageDOS =storageMapper.getStorageNameAll(state);
        return storageDOS;
    }
}
