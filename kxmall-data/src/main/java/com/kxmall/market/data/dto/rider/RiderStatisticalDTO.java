package com.kxmall.market.data.dto.rider;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: fy
 * @date: 2020/03/08 14:58
 **/
@Data
public class RiderStatisticalDTO implements Serializable {

    private Integer count;
    private Integer status;
}
