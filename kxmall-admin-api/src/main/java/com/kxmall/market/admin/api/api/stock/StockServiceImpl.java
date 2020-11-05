package com.kxmall.market.admin.api.api.stock;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.kxmall.market.biz.service.goods.GoodsBizService;
import com.kxmall.market.core.exception.AdminServiceException;
import com.kxmall.market.core.exception.ExceptionDefinition;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.component.CacheComponent;
import com.kxmall.market.data.component.LockComponent;
import com.kxmall.market.data.domain.CategoryDO;
import com.kxmall.market.data.domain.SpuDO;
import com.kxmall.market.data.domain.StockDO;
import com.kxmall.market.data.dto.StockDTO;
import com.kxmall.market.data.dto.goods.SkuDTO;
import com.kxmall.market.data.dto.goods.SpuDTO;
import com.kxmall.market.data.mapper.CategoryMapper;
import com.kxmall.market.data.mapper.StockMapper;
import com.kxmall.market.data.model.Page;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 库存接口服务
 *
 * @author kaixin
 * @date 2020/2/19
 */
@Service
@SuppressWarnings("all")
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CacheComponent cacheComponent;

    @Autowired
    private StockComponent stockComponent;

    @Autowired
    private LockComponent lockComponent;

    /**
     * 商品列表查询
     *
     * @param storageId
     * @param categoryId
     * @param name
     * @param status
     * @param page
     * @param limit
     * @param adminId
     * @return
     * @throws ServiceException
     * @author wxf
     * @date 2020/2/19
     */
    @Override
    public Page<StockDTO> list(Long storageId, Long categoryId, String name, Integer status, Integer page, Integer limit,String idsNotInJson, Long adminId) throws ServiceException {
        Integer offset = (page - 1) * limit;
        Integer size = limit;
        List<String> idsNotIn = null;
        if (!StringUtils.isEmpty(idsNotInJson)) {
            idsNotIn = JSONObject.parseArray(idsNotInJson, String.class);
        }
        List<StockDTO> stocks = stockMapper.selectStockListByStorageAndCategory(storageId, categoryId, name, status, offset, size,null,idsNotIn);
        for (StockDTO stock : stocks) {
            CategoryDO category = stock.getCategoryDO();
            if (category != null) {
                Long id = category.getId();
                //获取商品类目
                List<CategoryDO> categoryParentByChildId = categoryMapper.getCategoryParentByChildId(id);
                stock.setCategoryDOs(categoryParentByChildId);
            }
            SkuDTO skuDTO = stock.getSkuDTO();
            if (skuDTO != null) {
                SpuDO spuDO = stock.getSpuDO();
                if(spuDO != null) {
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
        Integer count = stockMapper.selectStockListByStorageAndCategoryCount(storageId, categoryId, name, status,idsNotIn);
        return new Page<>(stocks, page, limit, count);
    }

    /**
     * 上下架
     *
     * @param stockId
     * @param status
     * @param adminId
     * @return
     * @throws ServiceException
     */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StockDO freezeOrActivation(Long stockId, Integer status, Long adminId) throws ServiceException {
        StockDO stockDO = new StockDO();
        stockDO.setStatus(status);
        stockDO.setGmtUpdate(new Date());
        stockDO.setId(stockId);
        Integer id = stockMapper.update(stockDO, new EntityWrapper<StockDO>().eq("id", stockId));
        return stockDO;
    }

    @Override
    public String delete(Long stockId, Long adminId) throws ServiceException {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StockDO create(Long spuId, Long skuId, Long storageId, Integer status, Long stock, Long sales, Long frezzStock, Integer price, Long adminId) throws ServiceException {

        //新增时更新缓存 @author xiebo
        //==============
        List<StockDO> stockDOList = cacheComponent.getObjList(StockComponent.STOCK_LEFT, StockDO.class);
        stockDOList.stream().filter(t -> t.getStorageId() == storageId && t.getSpuId() == spuId).forEach(item -> {
            if (lockComponent.tryLock(StockComponent.SKU_ + item.getSkuId(), 300)) {
                item.setStock(item.getStock() + stock);
            }
        });
        cacheComponent.putObj(StockComponent.STOCK_LEFT, stockDOList, null);
        //=============

        StockDO stockDO = new StockDO();
        stockDO.setSpuId(spuId);
        stockDO.setSkuId(skuId);
        stockDO.setStorageId(storageId);
        stockDO.setStatus(status);
        stockDO.setStock(stock);
        stockDO.setSales(sales);
        stockDO.setFrezzStock(0L);
        stockDO.setPrice(price);
        Date now = new Date();
        stockDO.setGmtCreate(now);
        stockDO.setGmtUpdate(now);
        if (stockMapper.insert(stockDO) > 0) {
            /**
             * 释放缓存
             */
            cacheComponent.delPrefixKey(GoodsBizService.CA_SPU_PAGE_PREFIX);
            return stockDO;
        }
        throw new AdminServiceException(ExceptionDefinition.ADMIN_UNKNOWN_EXCEPTION);
    }

    @Override
    @Transactional
    public Integer updatePrice(Long stockId, Integer price, Long adminId) throws ServiceException {
        StockDO stockDO = new StockDO();
        stockDO.setGmtUpdate(new Date());
        stockDO.setPrice(price);
        stockDO.setId(stockId);
        return stockMapper.update(stockDO, new EntityWrapper<StockDO>().eq("id", stockId));
    }

    @Override
    public Page<SpuDTO> warningList(Long storageId, Integer page, Integer limit, Long categoryId, String name, Integer type, Integer minNum, Integer maxNum, Boolean showType, Long adminId) throws ServiceException {

        //类目查询
        LinkedList<Long> childrenIds = new LinkedList<>();
        if (categoryId != null && categoryId != 0L) {
            List<CategoryDO> childrenList = categoryMapper.selectList(new EntityWrapper<CategoryDO>().eq("parent_id", categoryId));
            if (!CollectionUtils.isEmpty(childrenList)) {
                //一级分类
                childrenList.forEach(item -> childrenIds.add(item.getId()));
                //使用in查询，就不需要等于查询
                categoryId = null;
            }
        }
        //查询制定页数的库存预警
        List<SpuDTO> spuDTOList = stockMapper.warningListByStorage(new RowBounds((page - 1) * limit, limit), storageId, categoryId, childrenIds, name, type, minNum, maxNum, showType);
        //查出总数
        Integer count = stockMapper.warningListByStorageCount(storageId, categoryId, childrenIds, name, type, minNum, maxNum, showType);
        return new Page<>(spuDTOList, page, limit, count);
    }

    /**
     *
     * @param storageId 仓库id
     * @param spuId 商品id
     * @param num 更新数量
     * @param adminId 管理员id
     * @return
     * @throws ServiceException
     */
    @Override
    @Transactional
    public Integer warningUpdate(Long storageId, Long spuId, Integer num, Long adminId) throws ServiceException {
        StockDO stockDO = new StockDO();
        stockDO.setGmtUpdate(new Date());
        stockDO.setWarningNum(num);
        return stockMapper.update(stockDO, new EntityWrapper<StockDO>().eq("storage_id", storageId).eq("spu_id",spuId));
    }
}
