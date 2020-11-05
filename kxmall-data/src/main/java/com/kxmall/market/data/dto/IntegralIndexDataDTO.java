package com.kxmall.market.data.dto;

import com.kxmall.market.data.dto.goods.SpuDTO;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 首页聚合接口DTO
 *
 * @author kaixin
 * @date 2019/7/14
 */
@Data
public class IntegralIndexDataDTO {

    private Map<String, List<AdvertisementDTO>> advertisement;

    private List<RecommendDTO> cheapRecommend;

    private List<RecommendDTO> salesTop;

    private List<SpuDTO> newTop;

    /**
     * 新鲜时报
     */
    private String newTimesContent;

}
