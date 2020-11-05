package com.kxmall.market.core.annotation.param;

import java.lang.annotation.*;

/**
 *
 * Description:
 * User: admin
 * Date: 2018-08-20
 * Time: 上午11:11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface Range {
    long min() default Long.MIN_VALUE;
    long max() default Long.MAX_VALUE;
}
