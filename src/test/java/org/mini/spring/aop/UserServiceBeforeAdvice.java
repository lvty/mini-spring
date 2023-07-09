package org.mini.spring.aop;

import java.lang.reflect.Method;

/**
 * <p>
 *      不是实现 MethodInterceptor 接口，而是实
 *      现 MethodBeforeAdvice 环绕拦截。在这个方法中我们可以获取到方法的一些信
 *      息，如果还开发了它的 MethodAfterAdvice 则可以两个接口一起实现。
 * </p>
 *
 * @author Administrator
 * @since 2023/7/9
 */
public class UserServiceBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println(method.getName() + " 方法被拦截");
    }
}
