package org.mini.spring.aop.framework;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.mini.spring.aop.AdvisedSupport;

import java.lang.reflect.Method;

/**
 * <p>
 * 使用Enhancer代理的类可以在运行期间为接口使用底层的ASM字节码增强技术完成代理对象的生产；
 * 因此， 被代理的对象不需要实现任何的接口
 * <p>
 * 可以扩展的用户拦截方法， 主要在Enhancer#setCallback中处理
 * </p>
 *
 * @author pp
 * @since 2023/6/11
 */
public class Cglib2AopProxy implements AopProxy {

    private final AdvisedSupport advisedSupport;

    public Cglib2AopProxy(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advisedSupport.getTargetSource().getTarget().getClass());
        //fixme: 此处代理对象的interface获取有问题
        enhancer.setInterfaces(advisedSupport.getTargetSource().getTargetClass());

        enhancer.setCallback(new DynamicAdvisedInterceptor(advisedSupport));
        return enhancer.create();
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor {
        private final AdvisedSupport advisedSupport;

        public DynamicAdvisedInterceptor(AdvisedSupport advisedSupport) {
            this.advisedSupport = advisedSupport;
        }

        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            CglibMethodInvocation cglibMethodInvocation = new CglibMethodInvocation(this.advisedSupport.getTargetSource().getTarget(), method, objects);

            if (this.advisedSupport.getMethodMatcher().matches(method, advisedSupport.getTargetSource().getTarget().getClass())) {
                return this.advisedSupport.getMethodInterceptor().invoke(cglibMethodInvocation);
            }

            return cglibMethodInvocation.proceed();
        }

        private static class CglibMethodInvocation extends ReflectiveMethodInvocation {

            private Object target;

            private Method method;

            private Object[] arguments;

            public CglibMethodInvocation(Object target, Method method, Object[] arguments) {
                super(target, method, arguments);
                this.target = target;
                this.method = method;
                this.arguments = arguments;
            }

            @Override
            public Object proceed() throws Throwable {
                return this.method.invoke(this.target, this.arguments);
            }
        }
    }


}
