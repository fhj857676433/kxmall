package com.kxmall.market.data.dto;

import lombok.Data;

/**
 *
 * Description:
 * User: admin
 * Date: 2019-07-08
 * Time: 上午8:38
 */
@Data
public class FootprintDTO extends SuperDTO {

    private Long userId;

    private Long spuId;

    private String spuTitle;

    private Integer spuPrice;

    private String spuImg;
}
