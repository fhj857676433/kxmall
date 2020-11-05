package com.kxmall.market.core.notify;

import com.kxmall.market.core.exception.ServiceException;

/**
 * Created by admin on 2019/7/1.
 */
public interface SMSClient {

    public SMSResult sendRegisterVerify(String phone, String verifyCode) throws ServiceException;

    public SMSResult sendBindPhoneVerify(String phone, String verifyCode) throws ServiceException;

    public SMSResult sendResetPasswordVerify(String phone, String verifyCode) throws ServiceException;

    public SMSResult sendAdminLoginVerify(String phone,String verifyCode) throws ServiceException;


}
