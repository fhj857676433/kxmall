package com.kxmall.market.data.dto.rider;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: fy
 * @date: 2020/03/08 14:44
 **/
@Data
public class RiderOrderStatisticalDTO implements Serializable {

    private Integer waitingCount;
    private Integer dispenseCount;
    private Integer timeoutCount;
    private Integer abnormalCount;
    private Integer completedCount;

}
