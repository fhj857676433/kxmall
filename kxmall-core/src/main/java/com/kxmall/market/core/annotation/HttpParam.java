package com.kxmall.market.core.annotation;

import java.lang.annotation.*;

/**
 *
 * Description:
 * User: admin
 * Date: 2018-03-25
 * Time: 下午1:12
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpParam {
    String name();
    HttpParamType type() default HttpParamType.COMMON;
    String description() default "";
    String valueDef() default "";
}
