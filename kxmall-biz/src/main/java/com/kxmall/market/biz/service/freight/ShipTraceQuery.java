package com.kxmall.market.biz.service.freight;

import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.dto.freight.ShipTraceDTO;

/**
 * Created by admin on 2019/7/10.
 */
public interface ShipTraceQuery {

    public ShipTraceDTO query(String shipNo, String shipCode) throws ServiceException;

}
