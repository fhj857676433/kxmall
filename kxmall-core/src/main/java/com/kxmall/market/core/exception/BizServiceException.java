package com.kxmall.market.core.exception;

/**
 * Created by admin on 2019/7/1.
 */
public class BizServiceException extends ServiceException {

    public BizServiceException(ServiceExceptionDefinition definition) {
        super(definition);
    }

    public BizServiceException(String message, int code) {
        super(message,code);
    }
}
