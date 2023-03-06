package org.pigliu.rediscase.annotation;

import java.lang.annotation.*;

/**
 * 是否需要签名标识
 * @author liufuqiang
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Signature {
}
