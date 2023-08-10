package org.pigliu.rediscase.annotation;

import cn.hutool.core.util.StrUtil;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

/**
 * TODO
 *
 * @author liufuqiang
 * @since 2023/8/7
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Conditional(ConditionalOnPropertyNotEmpty.OnPropertyNotEmptyCondition.class)
public @interface ConditionalOnPropertyNotEmpty {

    String value();

//    @Order(Ordered.HIGHEST_PRECEDENCE)
    class OnPropertyNotEmptyCondition implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Map<String, Object> attrs = metadata.getAnnotationAttributes(ConditionalOnPropertyNotEmpty.class.getName());
            if (attrs == null) {
                return false;
            }
            String propertyName = (String) attrs.get("value");
            return StrUtil.isNotBlank(context.getEnvironment().getProperty(propertyName));
        }
    }
}
