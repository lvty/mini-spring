package org.mini.spring.aop;


import org.mini.spring.aop.intercept.MethodInterceptor;

/**
 * <p>
 *     主要把拦截、匹配、代理的各项属性包装到一个类中，方便在Proxy实现类中使用
 * </p>
 *
 * @author pp
 * @since 2023/6/11
 */
public class AdvisedSupport {

    /**
     * 被代理的目标对象
     */
    private TargetSource targetSource;

    /**
     * 方法拦截器: 抽象接口， 由用户完成invoke方法， 做具体的处理
     */
    private MethodInterceptor methodInterceptor;

    /**
     * 方法匹配器： 这个对象由AspectJExpressionPointcut提供服务
     */
    private MethodMatcher methodMatcher;

    private boolean proxyTargetClass;

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }
}
