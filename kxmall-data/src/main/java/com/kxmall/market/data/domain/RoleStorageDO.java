package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: fy
 * @date: 2020/03/08 14:21
 **/
@Data
@TableName("kxmall_role_storage")
public class RoleStorageDO implements Serializable {

    @TableField("role_id")
    private Long roleId;

    @TableField("storage_id")
    private Long storageId;

}
