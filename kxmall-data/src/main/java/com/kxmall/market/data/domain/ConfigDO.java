package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.kxmall.market.data.annotation.DtoDescription;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * Description:
 * User: admin
 * Date: 2019-07-20
 * Time: 上午10:15
 */

@Data
@TableName("kxmall_config")
@AllArgsConstructor
@NoArgsConstructor
public class ConfigDO extends SuperDO {

    @DtoDescription(description="参数名称")
    @TableField("key_word")
    private String keyWord;

    @DtoDescription(description="参数值")
    @TableField("value_worth")
    private String valueWorth;

    @DtoDescription(description="参数描述")
    private String description;

    private Integer state;
}
