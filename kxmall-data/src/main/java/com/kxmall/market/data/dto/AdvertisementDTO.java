package com.kxmall.market.data.dto;

import lombok.Data;

/**
 * Created by admin on 2019/7/14.
 */
@Data
public class AdvertisementDTO extends SuperDTO {

    private Integer adType;

    private String title;

    private String url;

    private String imgUrl;

    private String outUrl;

    private Integer status;

    private String color;

    /**
     * 附加广告数据
     */
    private Object data;
}
