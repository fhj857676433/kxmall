package com.kxmall.market.biz.service.storage;

import java.io.InputStream;

/**
 *
 * Description:
 * User: admin
 * Date: 2019/11/26
 * Time: 21:33
 */
public interface StorageBizService {

    public String upload(String fileName, InputStream is, long contentLength, String contentType);

}
