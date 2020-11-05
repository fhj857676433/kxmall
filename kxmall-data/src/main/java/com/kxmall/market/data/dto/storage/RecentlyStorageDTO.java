package com.kxmall.market.data.dto.storage;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 定位最近仓库DTO传输类
 * @author: fy
 * @date: 2020/02/22 17:37
 **/
@Data
public class RecentlyStorageDTO implements Serializable {

    /*** 仓库主键ID*/
    private Long id;

    /*** 最近是否有仓库*/
    private Boolean haveStorage;

    /*** 最近仓库是否营业*/
    private Boolean businessState;

    /*** 营业起始时间*/
    private String businessStartTime;

    /*** 营业结束时间*/
    private String businessStopTime;

    /*** 配送起始时间*/
    private String deliveryStartTime;

    /*** 配送结束时间*/
    private String deliveryStopTime;

}
