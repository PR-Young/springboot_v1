package com.system.springbootv1.common.log;

import java.lang.annotation.*;

/**
 * @description:
 * @author: yy 2020/01/27
 **/
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    String value() default "";
}
