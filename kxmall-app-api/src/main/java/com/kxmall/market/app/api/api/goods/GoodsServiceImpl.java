package com.kxmall.market.app.api.api.goods;

import com.kxmall.market.biz.service.goods.GoodsBizService;
import com.kxmall.market.biz.service.groupshop.GroupShopBizService;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.dto.goods.SpuDTO;
import com.kxmall.market.data.model.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * 提供给app端口商品业务类
 *
 * @author kaixin
 * @date 2020/02/22
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Resource
    private GoodsBizService goodsBizService;

    @Resource
    private GroupShopBizService groupShopBizService;

    @Override
    public Page<SpuDTO> getGoodsPage(Integer pageNo, Integer pageSize, Long categoryId, String orderBy,Boolean isAsc, String title) throws ServiceException {
        return goodsBizService.getGoodsPage(pageNo, pageSize, categoryId, orderBy, isAsc, title);
    }

    @Override
    public SpuDTO getGoods(Long spuId, Long groupShopId, Long userId) throws ServiceException {
        //若团购Id不为空，则额外添加团购信息
        SpuDTO goods = goodsBizService.getGoods(spuId, userId);
        if (groupShopId != null) {
            goods.setGroupShop(groupShopBizService.getGroupShopById(groupShopId));
        }
        return goods;
    }

    @Override
    public Page<SpuDTO> getGoodsPageByStorage(Long storageId, Integer pageNo, Integer pageSize, Long categoryId, String orderBy, Boolean isAsc, String title) throws ServiceException {
        return goodsBizService.getGoodsPageByStorage(storageId,pageNo, pageSize, categoryId, orderBy, isAsc, title);
    }

    @Override
    public SpuDTO getGoodsByStorage(Long storageId, Long spuId, Long userId,Long activityId,Long couponId) throws ServiceException {
        return goodsBizService.getGoodsByStorage(storageId,spuId, userId,activityId,couponId);
    }
}
