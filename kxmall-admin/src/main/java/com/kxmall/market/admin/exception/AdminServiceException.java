package com.kxmall.market.admin.exception;

import com.kxmall.market.core.exception.ServiceException;
import com.kxmall.market.core.exception.ServiceExceptionDefinition;

/**
 *
 * @author admin
 * @date 2019/6/30
 */
public class AdminServiceException extends ServiceException {

    public AdminServiceException(ServiceExceptionDefinition exceptionDefinition) {
        super(exceptionDefinition);
    }

}
