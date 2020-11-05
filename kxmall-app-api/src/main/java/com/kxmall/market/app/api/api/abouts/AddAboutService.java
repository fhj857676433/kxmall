package com.kxmall.market.app.api.api.abouts;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.annotation.HttpParam;
import com.kxmall.market.core.annotation.HttpParamType;

import javax.sql.rowset.serial.SerialException;

@HttpOpenApi(group = "about",description = "人工客服")
public interface AddAboutService {
    @HttpMethod(description = "联系客服")
    public String about(
            @HttpParam(name = "userId", type = HttpParamType.USER_ID, description = "用户id") Long userId
    )throws SerialException;
}
