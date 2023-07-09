package org.mini.spring.aop.framework;

import org.mini.spring.aop.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * <p>
 * 实现MethodInvocation接口的一个包装类，参数信息包含：被调对象、调用的方法、入参信息
 * </p>
 *
 * @author pp
 * @since 2023/6/11
 */
public class ReflectiveMethodInvocation implements MethodInvocation {
    private Object target;

    private Method method;

    private Object[] arguments;

    public ReflectiveMethodInvocation(Object target, Method method, Object[] arguments) {
        this.target = target;
        this.method = method;
        this.arguments = arguments;
    }

    public Object getTarget() {
        return target;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public Object proceed() throws Throwable {
        return this.method.invoke(this.target, this.arguments);
    }

    @Override
    public Object getThis() {
        return target;
    }
}
