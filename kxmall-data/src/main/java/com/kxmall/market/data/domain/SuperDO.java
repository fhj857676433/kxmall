package com.kxmall.market.data.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.kxmall.market.data.annotation.DtoDescription;
import lombok.Data;

import java.util.Date;

/**
 *
 * @author kaixin
 * @date 2019/6/30
 */
@Data
public class SuperDO {
    @DtoDescription(description = "自增主键")
    @TableId(type = IdType.AUTO)
    private Long id;
    @DtoDescription(description = "更新时间")
    @TableField("gmt_update")
    private Date gmtUpdate;
    @DtoDescription(description = "创建时间")
    @TableField("gmt_create")
    private Date gmtCreate;

}
