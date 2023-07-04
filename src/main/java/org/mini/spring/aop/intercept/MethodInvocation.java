package org.mini.spring.aop.intercept;

import java.lang.reflect.Method;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author Administrator
 * @since 2023/7/4
 */
public interface MethodInvocation extends Invocation {

    /**
     * Get the method being called.
     * <p>This method is a friendly implementation of the
     * @return the method being called
     */
    Method getMethod();

}
