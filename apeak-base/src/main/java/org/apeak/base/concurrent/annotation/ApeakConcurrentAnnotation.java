package org.apeak.base.concurrent.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: HZF
 * @Date: 2020/6/6 22:51
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface ApeakConcurrentAnnotation {

    int maxNumber() default 10;

    long timeWait() default 1000;
}
