package com.kxmall.market.data.dto;

import lombok.Data;

import java.util.List;

@Data
public class RiderCycleDTO {

    private Long id;

    /**配送员名称*/
    private String name;

    /**联系电话*/
    private String phone;

    /**仓库主键ID*/
    private Long storageId;

    private String deliveryStart;

    private String deliveryEnd;

    private List<Integer> weekNumberIds;
}
