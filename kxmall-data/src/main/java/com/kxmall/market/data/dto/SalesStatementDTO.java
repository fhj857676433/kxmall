package com.kxmall.market.data.dto;

import com.kxmall.market.data.annotation.DtoDescription;
import lombok.Data;

import java.util.List;

/**
 * @author: Mr Wang
 * @date: 2020/3/9
 * @time: 23:23
 */

@Data
public class SalesStatementDTO extends SuperDTO {

    @DtoDescription(description = "一级类目ID")
    private Long categoryId;

    @DtoDescription(description = "类目")
    private String categoryTitle;

    @DtoDescription(description = "总销量")
    private Long totalSales;

    @DtoDescription(description = "总销售额")
    private Float totalSalesVolume;

    @DtoDescription(description = "top详情")
    private List<SalesTopDTO> salesTopDTOs;


}
