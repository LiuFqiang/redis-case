package org.pigliu.rediscase.annotation;

import org.aspectj.lang.annotation.Before;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/8/10
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface BknBoard {
}
