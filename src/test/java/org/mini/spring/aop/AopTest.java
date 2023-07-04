package org.mini.spring.aop;

import org.junit.Test;
import org.mini.spring.aop.aspectj.AspectJExpressionPointcut;
import org.mini.spring.aop.framework.ReflectiveMethodInvocation;
import org.mini.spring.aop.intercept.MethodInterceptor;
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
    public void testAopCase1() {
        // 目标对象
        UService targetObj = new UService();

        IUserService proxyInstance = (IUserService) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                targetObj.getClass().getInterfaces(),
                new InvocationHandler() {
                    // 方法匹配器: 对UserService的所有方法进行拦截并添加监控信息和打印处理
                    MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* org.mini.spring.aop.IUserService.*(..))");

                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        if (methodMatcher.matches(method, targetObj.getClass())) {
                            // 方法拦截器

                            // 用户自定义的拦截方法操作
                            MethodInterceptor methodInterceptor = invocation -> {
                                long startTs = System.currentTimeMillis();

                                try {
                                    return invocation.proceed();
                                } finally {
                                    System.out.println("监控 - Begin By Aop");
                                    System.out.println("方法名称：" + invocation.getMethod().getName());
                                    System.out.println("方法耗时：" + (System.currentTimeMillis() - startTs) + " ms");
                                    System.out.println("监控 - End\n");
                                }
                            };

                            // 反射调用
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
