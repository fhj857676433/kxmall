package com.kxmall.market.data.domain;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("kxmall_region")
public class RegionDO extends SuperDO {

    /*** 代码*/
    private String code;
    /*** 名称*/
    private String name;
    /*** 简称*/
    @TableField("short_name")
    private String shortName;
    /*** 上级代码*/
    @TableField("superior_code")
    private String superiorCode;
    /*** 经度*/
    private String lng;
    /*** 纬度*/
    private String lat;
    /*** 排序*/
    private Integer sort;
    /*** 备注*/
    private String ramark;
    /*** 状态*/
    private Integer state;
    /*** 租户id*/
    @TableField("tenant_code")
    private String tenantCode;
    /*** 级别*/
    private Integer level;
}
