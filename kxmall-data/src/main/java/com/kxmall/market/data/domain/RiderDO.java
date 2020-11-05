package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: fy
 * @date: 2020/02/27 20:30
 **/
@Data
@TableName("rider")
@EqualsAndHashCode(callSuper = true)
public class RiderDO extends SuperDO {

    /*** 配送员名称*/
    private String name;

    /*** 密码*/
    private String password;

    /*** 仓库主键ID*/
    @TableField("storage_id")
    private Long storageId;

    /*** 开始配送时间*/
    @TableField("delivery_start")
    private String deliveryStart;

    /*** 结束配送时间*/
    @TableField("delivery_end")
    private String deliveryEnd;

    /*** 联系电话*/
    private String phone;

    /*** 配送员状态*/
    private Integer state;

    /*** 开工状态*/
    @TableField("work_state")
    private Integer workState;

    @TableField("gmt_update_user_id")
    private Long gmtUpdateUserId;

    /*** 创建人*/
    @TableField("gmt_create_user_id")
    private Long gmtCreateUserId;
}
