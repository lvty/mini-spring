package org.mini.spring.aop.framework;

import org.mini.spring.aop.AdvisedSupport;
import org.mini.spring.aop.intercept.MethodInterceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <p>
 * 基于JDK实现的动态代理，分别实现接口AopProxy, InvocationHandler
 * 目的是： 把代理对象getProxy和反射调用方法分开来处理
 * </p>
 *
 * @author pp
 * @since 2023/6/11
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    private final AdvisedSupport advised;

    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }


    /**
     * 主要处理匹配的方法后，使用用户自定义的方法拦截器实现， 反射调用
     * ReflectiveMethodInvocation: 给出的调用的方法的包装信息： 目标对象、方法、入参
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass())) {
            MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(
                    advised.getTargetSource().getTarget(),
                    method,
                    args
            ));
        }

        return method.invoke(advised.getTargetSource().getTarget(), args);
    }

    /**
     * 返回代理的对象
     * @return
     */
    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                advised.getTargetSource().getTargetClass(),
                this
        );
    }
}
