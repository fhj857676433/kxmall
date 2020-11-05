package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * Created by admin on 2019/4/8.
 */
@TableName("kxmall_role")
@Data
public class RoleDO extends SuperDO {

    private String name;

    private String desc;

    private Integer status;

}
