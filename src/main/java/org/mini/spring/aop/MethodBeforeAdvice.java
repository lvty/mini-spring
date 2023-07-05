package org.mini.spring.aop;

import java.lang.reflect.Method;

/**
 * <p>
 *      在 Spring 框架中， Advice 都是通过方法拦截器 MethodInterceptor 实现的。环
 *      绕 Advice 类似一个拦截器的链路， Before Advice、 After advice
 * </p>
 *
 * @author Administrator
 * @since 2023/7/5
 */
public interface MethodBeforeAdvice extends BeforeAdvice {


    /**
     * Callback before a given method is invoked.
     * @param method
     * @param args
     * @param target
     * @throws Throwable
     */
    void before(Method method, Object[] args, Object target) throws Throwable;
}
