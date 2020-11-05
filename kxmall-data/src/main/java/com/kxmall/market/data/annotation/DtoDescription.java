package com.kxmall.market.data.annotation;

import java.lang.annotation.*;

/**
 *
 * Description:
 * User: admin
 * Date: 2018-03-25
 * Time: 下午1:12
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DtoDescription {
    String description() default "参数未注解";
}
