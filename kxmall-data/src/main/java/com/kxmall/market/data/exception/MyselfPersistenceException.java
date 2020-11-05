package com.kxmall.market.data.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: fy
 * @date: 2020/04/26 22:03
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class MyselfPersistenceException extends RuntimeException {

    public MyselfPersistenceException() { }

    public MyselfPersistenceException(String message) {
        super(message);
    }
}
