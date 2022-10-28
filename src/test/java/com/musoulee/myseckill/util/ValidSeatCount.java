package com.musoulee.myseckill.util;

import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @description:
 * @author: musou
 * @Date: 2022/10/26 11:15
 */
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidSeatCount {
    String message() default "我的小车坐不下了！";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
