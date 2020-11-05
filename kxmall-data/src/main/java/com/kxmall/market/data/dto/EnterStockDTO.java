package com.kxmall.market.data.dto;

import com.kxmall.market.data.annotation.DtoDescription;
import com.kxmall.market.data.domain.AdminDO;
import com.kxmall.market.data.domain.EnterStockDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *@author wxf
 *@date  2029/3/1 - 21:29
 * 入库DO
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnterStockDTO extends EnterStockDO {


    @DtoDescription(description = "创建人")
    private AdminDO gmtCreateUser;

    @DtoDescription(description = "更新人")
    private AdminDO gmtUpdateUser;

}
