package org.mini.spring.stereotype;

import java.lang.annotation.*;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author Administrator
 * @since 2023/7/11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface Component {

    String value() default "";
}
