package com.kxmall.market.app.api.api.newtimes;

import com.kxmall.market.core.annotation.HttpMethod;
import com.kxmall.market.core.annotation.HttpOpenApi;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.domain.NewTimesDO;

import java.util.List;

@HttpOpenApi(group = "newtimes",description = "新鲜时报")
public interface newtimesService {

    @HttpMethod(description = "查询所有仓库的新鲜时报")
    public List<NewTimesDO> getNewTimes() throws ServiceException;
}
