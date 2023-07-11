package org.mini.spring.context.annotation;

import java.lang.annotation.*;

/**
 * <p>
 *     用于配置Bean作用域的自定义注解，方便通过注解配置 Bean 对象的时候，拿到 Bean
 *     对象的作用域。 不过一般都使用默认的 singleton
 * </p>
 *
 * @author Administrator
 * @since 2023/7/11
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Scope {

    String value() default "singleton";
}
