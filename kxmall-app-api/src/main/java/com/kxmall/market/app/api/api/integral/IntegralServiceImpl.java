package com.kxmall.market.app.api.api.integral;

import com.kxmall.market.app.api.api.advertisement.AdvertisementService;
import com.kxmall.market.biz.service.goods.GoodsBizService;
import com.kxmall.market.biz.service.newTimes.NewTimesBizService;
import com.kxmall.market.biz.service.recommend.RecommendBizService;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.domain.AdvertisementDO;
import com.kxmall.market.data.dto.AdvertisementDTO;
import com.kxmall.market.data.dto.IntegralIndexDataDTO;
import com.kxmall.market.data.dto.RecommendDTO;
import com.kxmall.market.data.dto.goods.SpuDTO;
import com.kxmall.market.data.enums.AdvertisementType;
import com.kxmall.market.data.enums.RecommendType;
import com.kxmall.market.data.model.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author admin
 * @date 2019/7/14
 */
@Service
public class IntegralServiceImpl implements IntegralService {

    @Resource
    private GoodsBizService goodsBizService;

    @Resource
    private NewTimesBizService newTimesBizService;

    @Resource
    private RecommendBizService recommendBizService;

    @Resource
    private AdvertisementService advertisementService;

    @Override
    public IntegralIndexDataDTO getIndexData() throws ServiceException {
        //分类
        List<AdvertisementDO> activeAd = advertisementService.getActiveAd(null);
        Map<String, List<AdvertisementDTO>> adDTOMap = activeAd.stream().map(item -> {
            AdvertisementDTO advertisementDTO = new AdvertisementDTO();
            BeanUtils.copyProperties(item, advertisementDTO);
            return advertisementDTO;
        }).collect(Collectors.groupingBy(item -> "t" + item.getAdType()));
        List<AdvertisementDTO> categoryPickAd = adDTOMap.get("t" + AdvertisementType.CATEGORY_PICK.getCode());
        //封装 分类精选 商品
        if (!CollectionUtils.isEmpty(categoryPickAd)) {
            for (AdvertisementDTO item : categoryPickAd) {
                Page<SpuDTO> pickPage = goodsBizService.getGoodsPage(1, 10, new Long(item.getUrl().substring(item.getUrl().lastIndexOf("=") + 1)), "sales", false, null);
                item.setData(pickPage.getItems());
            }
        }
        IntegralIndexDataDTO integralIndexDataDTO = new IntegralIndexDataDTO();
        integralIndexDataDTO.setAdvertisement(adDTOMap);

        /**
         * 橱窗推荐
         */
        List<RecommendDTO> windowRecommend = recommendBizService.getRecommendByType(0L, RecommendType.CHEAP.getCode(), 1, 10).getItems();
        integralIndexDataDTO.setCheapRecommend(windowRecommend);

        /**
         * 销量冠军
         */
        /*List<SpuDTO> salesTop = goodsBizService.getGoodsPage(1, 8, null, "sales", false, null).getItems();
        integralIndexDataDTO.setSalesTop(salesTop);*/

        /**
         * 最近上新
         */
        List<SpuDTO> newTop = goodsBizService.getGoodsPage(1, 8, null, "id", false, null).getItems();
        integralIndexDataDTO.setNewTop(newTop);
        return integralIndexDataDTO;
    }

    @Override
    public IntegralIndexDataDTO getIndexDataByStorage(Long storageId) throws ServiceException {
        //分类
        List<AdvertisementDO> activeAd = advertisementService.getActiveAd(null);
        Map<String, List<AdvertisementDTO>> listMap = activeAd.stream().map(item -> {
            AdvertisementDTO advertisementDTO = new AdvertisementDTO();
            BeanUtils.copyProperties(item, advertisementDTO);
            return advertisementDTO;
        }).collect(Collectors.groupingBy(item -> "t" + item.getAdType()));
        List<AdvertisementDTO> categoryPickAd = listMap.get("t" + AdvertisementType.CATEGORY_PICK.getCode());
        //封装 分类精选 商品
        if (!CollectionUtils.isEmpty(categoryPickAd)) {
            Page<SpuDTO> pickPage;
            for (AdvertisementDTO item : categoryPickAd) {
                pickPage = goodsBizService.getGoodsPage(1, 10, new Long(item.getUrl().substring(item.getUrl().lastIndexOf("=") + 1)), "sales", false, null);
                item.setData(pickPage.getItems());
            }
        }
        IntegralIndexDataDTO integralIndexDataDTO = new IntegralIndexDataDTO();
        integralIndexDataDTO.setAdvertisement(listMap);

        // 销量冠军
        List<RecommendDTO> salesRecommend = recommendBizService.getRecommendByType(storageId, RecommendType.HOT.getCode(), 1, 10).getItems();
        integralIndexDataDTO.setSalesTop(salesRecommend);

        // 最近上新
        List<SpuDTO> newTop = goodsBizService.getGoodsPageByStorage(storageId, 1, 10, null, "a.id", false, null).getItems();
        integralIndexDataDTO.setNewTop(newTop);

        // 特价推荐
        List<RecommendDTO> cheapRecommend = recommendBizService.getRecommendByType(storageId, RecommendType.CHEAP.getCode(), 1, 10).getItems();
        integralIndexDataDTO.setCheapRecommend(cheapRecommend);

        // 新鲜时报
        integralIndexDataDTO.setNewTimesContent(newTimesBizService.getNewTimesContentByStorageId(storageId));
        return integralIndexDataDTO;
    }

}
