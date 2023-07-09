package org.mini.spring.aop.framework.adapter;

import org.mini.spring.aop.MethodBeforeAdvice;
import org.mini.spring.aop.intercept.MethodInterceptor;
import org.mini.spring.aop.intercept.MethodInvocation;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author Administrator
 * @since 2023/7/5
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor {

    private MethodBeforeAdvice methodBeforeAdvice;

    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice methodBeforeAdvice) {
        this.methodBeforeAdvice = methodBeforeAdvice;
    }

    /**
     * advice.before 则是用于自己实现 MethodBeforeAdvice 接口后做的相应处理
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        this.methodBeforeAdvice.before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return invocation.proceed();
    }
}
