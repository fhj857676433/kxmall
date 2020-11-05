package com.kxmall.market.core.exception;

/**
 * Created by admin on 2019/7/1.
 */
public class AppServiceException extends ServiceException {

    public AppServiceException(ServiceExceptionDefinition definition) {
        super(definition);
    }

    public AppServiceException(String message, int code) {
        super(message,code);
    }
}
