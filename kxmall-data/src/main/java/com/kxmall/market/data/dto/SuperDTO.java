package com.kxmall.market.data.dto;

import com.kxmall.market.data.annotation.DtoDescription;
import lombok.Data;

import java.util.Date;

/**
 *
 * @author admin
 * @date 2019/7/1
 */
@Data
public class SuperDTO {

    @DtoDescription(description = "自增主键")
    private Long id;
    @DtoDescription(description = "更新时间")
    private Date gmtUpdate;
    @DtoDescription(description = "创建时间")
    private Date gmtCreate;

}
