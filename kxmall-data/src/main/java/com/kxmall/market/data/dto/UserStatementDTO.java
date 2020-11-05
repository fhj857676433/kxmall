package com.kxmall.market.data.dto;

import com.kxmall.market.data.annotation.DtoDescription;
import lombok.Data;

/**
 * @author: Mr Wang
 * @date: 2020/3/9
 * @time: 23:23
 */

@Data
public class UserStatementDTO extends SuperDTO {

    @DtoDescription(description = "统计日期")
    private String statementDate;

    @DtoDescription(description = "总用户数")
    private Integer totalUser;

    @DtoDescription(description = "新注册用户数")
    private Integer newUser;

    @DtoDescription(description = "在线用户数")
    private Integer OnlineUser;

    @DtoDescription(description = "下单用户数")
    private Integer orderUser;

    @DtoDescription(description = "首单用户数")
    private Integer firstOrderUser;

}
