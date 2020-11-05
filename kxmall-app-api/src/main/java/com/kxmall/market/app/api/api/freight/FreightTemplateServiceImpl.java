package com.kxmall.market.app.api.api.freight;

import com.kxmall.market.biz.service.freight.FreightBizService;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.dto.order.OrderRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * Description:
 * User: admin
 * Date: 2019-07-07
 * Time: 下午7:50
 */
@Service
public class FreightTemplateServiceImpl implements FreightTemplateService {

    @Autowired
    private FreightBizService freightBizService;

    @Override
    public Integer getFreightMoney(Long userId, OrderRequestDTO orderRequestDTO) throws ServiceException {
        return freightBizService.getFreightMoney(orderRequestDTO);
    }
}
