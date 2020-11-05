package com.kxmall.market.data.dto;

import com.kxmall.market.data.domain.SuperDO;
import lombok.Data;

import java.math.BigDecimal;


/**
 * @author kaixin
 */
@Data
public class StorageDTO extends SuperDO {

    /*** 名称*/
    private String name;

    /*** 省*/
    private Long province;

    /*** 市*/
    private Long city;

    /*** 区（县）*/
    private Long county;

    /*** 区域编码*/
    private String adcode;

    /*** 详细地址*/
    private String address;

    /*** 仓库管理员电话*/
    private String phone;

    /*** 备注*/
    private String remark;

    /*** 状态*/
    private Integer state;

    /*** 仓库管理员姓名*/
    private String leaderName;

    /*** 配送半径*/
    private Integer deliveryRadius;

    /*** 营业起始时间*/
    private String businessStartTime;

    /*** 营业结束时间*/
    private String businessStopTime;

    /*** 配送起始时间*/
    private String deliveryStartTime;

    /*** 配送结束时间*/
    private String deliveryStopTime;

    /*** 营业状态*/
    private Integer operatingState;

    /*** 经度*/
    private BigDecimal longitude;

    /*** 纬度*/
    private BigDecimal latitude;

    /*** 更新人*/
    private Long gmtUpdateUserId;

    /*** 创建人*/
    private Long gmtCreateUserId;

}
