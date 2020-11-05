package com.kxmall.market.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kxmall.market.biz.service.goods.GoodsBizService;
import com.kxmall.market.data.component.CacheComponent;
import com.kxmall.market.data.domain.CategoryDO;
import com.kxmall.market.data.domain.SkuDO;
import com.kxmall.market.data.domain.SpuDO;
import com.kxmall.market.data.domain.StockDO;
import com.kxmall.market.data.domain.StorageDO;
import com.kxmall.market.data.dto.ErrorDataDTO;
import com.kxmall.market.data.dto.StockDTO;
import com.kxmall.market.data.dto.StockExcelDTO;
import com.kxmall.market.data.dto.goods.SkuDTO;
import com.kxmall.market.admin.service.StockFileService;
import com.kxmall.market.data.mapper.CategoryMapper;
import com.kxmall.market.data.mapper.SkuMapper;
import com.kxmall.market.data.mapper.SpuMapper;
import com.kxmall.market.data.mapper.StockMapper;
import com.kxmall.market.data.mapper.StorageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: Mr Wang
 * @date: 2020/2/23
 * @time: 9:10
 */

@Service
@SuppressWarnings("all")
public class StockFileServiceImpl implements StockFileService {



    private final static String ERROR_DATA = "ERROR_DATA";

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 批量添加库存
     *
     * @return 响应结果
     * @author wxf
     * @date 2020/2/22
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<StockDTO> insertBatch(List<StockExcelDTO> list,HttpServletRequest request){
        int count = 0;
        List<Long> ids = new ArrayList<>();
        //错误数据
        List<StockExcelDTO> errorData = new ArrayList<>();
        for (StockExcelDTO stockExcelDTO:list) {
            String barCode = stockExcelDTO.getBarCode();
            Long storageId = stockExcelDTO.getStorageId();
            //判断此仓库中是否有该商品
            Long skuId = stockMapper.selectExistByStorageIdAndBarCode(storageId, barCode);
            if(skuId == null) {
                List<SkuDO> skuDOList = skuMapper.selectList(new EntityWrapper<SkuDO>().eq("bar_code", barCode));
                    if(skuDOList != null && skuDOList.size() > 0){
                        SkuDO skuDO = skuDOList.get(0);
                        skuId = skuDO.getId();
                        StockDO stockDO = new StockDO();
                        stockDO.setSpuId(skuDO.getSpuId());
                        stockDO.setStorageId(storageId);
                        stockDO.setSkuId(skuId);
                        Integer status = stockExcelDTO.getStatus();
                        Integer price = stockExcelDTO.getPrice();
                        Long stock = stockExcelDTO.getStock();
                        if(status != null && price != null && stock != null){
                            stockDO.setStatus(status);
                            stockDO.setPrice(price);
                            stockDO.setStock(stock);
                            stockDO.setFrezzStock(0L);
                            stockDO.setSales(0L);
                            Date now = new Date();
                            stockDO.setGmtCreate(now);
                            stockDO.setGmtUpdate(now);
                            Integer flag = stockMapper.insert(stockDO);
                            if (flag > 0) {
                                count++;
                                ids.add(stockDO.getId());
                            }
                     }else{
                        stockExcelDTO.setMsg("信息不全!");
                        errorData.add(stockExcelDTO);
                    }
                }else{
                        stockExcelDTO.setMsg("条码信息错误!");
                        errorData.add(stockExcelDTO);
                }
            }else{
                stockExcelDTO.setMsg("该仓库下已存在该商品!");
                errorData.add(stockExcelDTO);
            }
        }
        if(errorData.size() > 0){
            //缓存错误信息
            String ipAddr = getIpAddr(request);
            cacheComponent.putObj(ERROR_DATA+"_"+ipAddr,errorData,600);
        }
        if(ids.size() > 0) {
            cacheComponent.delPrefixKey(GoodsBizService.CA_SPU_PAGE_PREFIX);
            List<StockDTO> stockDTOS = stockMapper.selectStockListByStorageAndCategory(null, null, null, null, 0, 20, ids,null);
            for (StockDTO stock:stockDTOS) {
                CategoryDO category = stock.getCategoryDO();
                if(category != null) {
                    Long id = category.getId();
                    //获取商品类目
                    List<CategoryDO> categoryParentByChildId = categoryMapper.getCategoryParentByChildId(id);
                    stock.setCategoryDOs(categoryParentByChildId);
                }
                SkuDTO skuDTO = stock.getSkuDTO();
                if(skuDTO != null) {
                    SpuDO spuDO = stock.getSpuDO();
                    if (spuDO != null) {
                        Integer originalPrice = spuDO.getOriginalPrice();
                        Integer price = stock.getPrice();
                        //计算折扣
                        if (price != null && originalPrice != null && originalPrice != 0) {
                            Double disCount = (price * 10.0) / originalPrice;
                            DecimalFormat df = new DecimalFormat();
                            df.applyPattern("0.00");
                            disCount = Double.parseDouble(df.format(disCount));
                            skuDTO.setDiscount(disCount);
                        }
                    }
                }
            }
            return  stockDTOS;
        }
        return new ArrayList<>();
    }



    @Autowired
    private SpuMapper spuMapper;

    /**
     * 库存模板
     * @return
     */

    @Override
    public List<StockExcelDTO> stockExport(){
        List<StockDTO> stockDTOS = stockMapper.selectStockListByStorageAndCategory(null, null, null, null, null, null,null,null);
        List<StockExcelDTO> stockExcelDTOS = new ArrayList<>();
        for (StockDTO stockDTO:stockDTOS) {
            StockExcelDTO stockExcelDTO = new StockExcelDTO();
            stockExcelDTO.setSpuId(stockDTO.getSpuId());
            stockExcelDTO.setStorageId(stockDTO.getStorageId());
            StorageDO storageDO = stockDTO.getStorageDO();
            if(storageDO != null) {
                stockExcelDTO.setName(storageDO.getName());
            }
            SkuDTO skuDTO = stockDTO.getSkuDTO();
            if(skuDTO != null) {
                stockExcelDTO.setBarCode(skuDTO.getBarCode());
                stockExcelDTO.setTitle(skuDTO.getTitle());
            }
            stockExcelDTOS.add(stockExcelDTO);
        }
        return stockExcelDTOS;
    }

    @Autowired
    private StorageMapper storageMapper;

    @Override
    public List<ErrorDataDTO> exportErrorData(HttpServletRequest request) {
        String ipAddr = getIpAddr(request);
        String key = ERROR_DATA+"_"+ipAddr;
        if(cacheComponent.hasKey(key)){
            List<ErrorDataDTO> objList = cacheComponent.getObjList(key, ErrorDataDTO.class);
            for (ErrorDataDTO err:objList) {
                Long storageId = err.getStorageId();
                if(storageId != null){
                    StorageDO storageDO = storageMapper.selectStorageById(storageId);
                    err.setName(storageDO.getName());
                }
            }
            return objList;
        }
        return null;
    }

    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
