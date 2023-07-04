package org.mini.spring.aop.intercept;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author Administrator
 * @since 2023/7/4
 */
public interface MethodInterceptor {


    Object invoke(MethodInvocation invocation) throws Throwable;
}
