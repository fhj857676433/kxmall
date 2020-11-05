package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("kxmall_storage")
public class StorageDO extends SuperDO {

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
    @TableField("leader_name")
    private String leaderName;

    /*** 配送半径*/
    @TableField("delivery_radius")
    private Integer deliveryRadius;

    /*** 营业起始时间*/
    @TableField("business_start_time")
    private String businessStartTime;

    /*** 营业结束时间*/
    @TableField("business_stop_time")
    private String businessStopTime;

    /*** 配送起始时间*/
    @TableField("delivery_start_time")
    private String deliveryStartTime;

    /*** 配送结束时间*/
    @TableField("delivery_stop_time")
    private String deliveryStopTime;

    /*** 营业状态*/
    @TableField("operating_state")
    private Integer operatingState;

    /*** 经度*/
    private BigDecimal longitude;

    /*** 纬度*/
    private BigDecimal latitude;

    /*** 是否自动分配订单*/
    private Integer automatic;

    /*** 更新人*/
    @TableField("gmt_update_user_id")
    private Long gmtUpdateUserId;

    /*** 创建人*/
    @TableField("gmt_create_user_id")
    private Long gmtCreateUserId;

}
