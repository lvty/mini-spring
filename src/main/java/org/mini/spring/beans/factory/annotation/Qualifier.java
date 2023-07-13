package org.mini.spring.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author Administrator
 * @since 2023/7/12
 */
@Target({ElementType.METHOD, ElementType.FIELD,
        ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Qualifier {

    String value() default "";
}
