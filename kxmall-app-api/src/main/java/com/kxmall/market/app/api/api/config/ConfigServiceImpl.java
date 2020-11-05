package com.kxmall.market.app.api.api.config;

import com.kxmall.market.biz.service.config.ConfigBizService;
import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.data.dto.ConfigDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2019/7/21.
 */
@Service
public class ConfigServiceImpl implements ConfigService{

    @Autowired
    private ConfigBizService configBizService;

    @Override
    public ConfigDTO getMerchantConfig() throws ServiceException {
        return configBizService.getMerchantConfig();
    }
}
