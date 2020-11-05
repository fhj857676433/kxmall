package com.kxmall.market.biz.service.compensation;

import java.lang.annotation.*;

/**
 * @description: 补偿注解
 * @author: fy
 * @date: 2020/03/11 17:26
 **/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Compensation {

    // 是否启用
    boolean require() default true;

//    // 补偿次数
//    int compensationNum() default 1;

    // 执行遇到错误是否继续补偿
    boolean errorContinue() default true;

    // 模块名称
    String moduleName() default "";
}
