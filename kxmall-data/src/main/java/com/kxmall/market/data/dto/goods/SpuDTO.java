package com.kxmall.market.data.dto.goods;

import com.kxmall.market.data.annotation.DtoDescription;
import com.kxmall.market.data.domain.SkuDO;
import com.kxmall.market.data.domain.SpuAttributeDO;
import com.kxmall.market.data.dto.CategoryDTO;
import com.kxmall.market.data.dto.StockDTO;
import com.kxmall.market.data.dto.StorageDTO;
import com.kxmall.market.data.dto.SuperDTO;
import com.kxmall.market.data.dto.appraise.AppraiseResponseDTO;
import com.kxmall.market.data.dto.freight.FreightTemplateDTO;
import com.kxmall.market.data.model.Page;
import lombok.Data;

import java.util.List;

/**
 *
 * @author admin
 * @date 2019/7/2
 */
@Data
public class SpuDTO extends SuperDTO {

    /**
     * 目前暂留
     */
    private List<SkuDO> skuList;

    private Integer originalPrice;

    private String title;

    /**
     * 商品规格id
     */
    private Long skuId;
    /**
     * 主图
     */
    private String img;

    /**
     * 后面的图，仅在详情接口才出现
     */
    private List<String> imgList;

    @DtoDescription(description = "活动id")
    private Long activityId;

    @DtoDescription(description = "优惠券id")
    private Long couponId;

    private String detail;

    private String description;

    private Long categoryId;

    private List<Long> categoryIds;

    private List<CategoryDTO> categoryList;

    private List<SpuAttributeDO> attributeList;

    /**
     * 商品的第一页(前10条)评价
     */
    private Page<AppraiseResponseDTO> appraisePage;

    /**
     * 库存信息集合 一个商品，对应一个规格
     */
    private StockDTO stockDto;

    /**
     * 库存信息
     */
    private StorageDTO storageDto;

    /**
     * 类目对象
     */
    private CategoryDTO categoryDto;

    /**
     * 规格对象
     */
    private SkuDTO skuDto;

    /**
     * 商品现在携带的团购信息
     */
    private GroupShopDTO groupShop;

    private String unit;

    private Long freightTemplateId;

    private FreightTemplateDTO freightTemplate;

    private Boolean collect;

    private Integer status;

}
