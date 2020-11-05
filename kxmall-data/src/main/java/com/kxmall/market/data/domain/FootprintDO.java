package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * Description:
 * User: admin
 * Date: 2019-07-08
 * Time: 上午8:30
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("kxmall_footprint")
public class FootprintDO extends SuperDO {

    @TableField("user_id")
    private Long userId;

    @TableField("spu_id")
    private Long spuId;
}
