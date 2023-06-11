package org.mini.spring.aop;

import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.Test;
import org.mini.spring.aop.aspectj.AspectJExpressionPointcut;
import org.mini.spring.aop.framework.ReflectiveMethodInvocation;
import org.mini.spring.beans.service.UserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/6/11
 */
public class AopTest {

    /**
     * 包含： 方法的匹配、反射的调用、用户自定义拦截的方法实现
     */
    @Test
    public void testAopCase1(){
        // 目标对象
        Object targetObj = new UserService();

        UserService proxyInstance = (UserService)Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                targetObj.getClass().getInterfaces(),
                new InvocationHandler() {
                    // 方法匹配器: 对UserService的所有方法进行拦截并添加监控信息和打印处理
                    MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* org.mini.spring.beans.service.*(..)");

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        if (methodMatcher.matches(method, targetObj.getClass())) {
                            // 方法拦截器
                            MethodInterceptor methodInterceptor = invocation -> {
                                return invocation.proceed;
                            }

                            return methodInterceptor.invoke(new ReflectiveMethodInvocation(targetObj, method, args));
                        }
                        return method.invoke(targetObj, args);
                    }
                }
        );

        String s = proxyInstance.queryUserInfo();
        System.out.println(s);
    }
}
