package org.mini.spring.aop.framework;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.mini.spring.aop.AdvisedSupport;

/**
 * <p>
 *     使用Enhancer代理的类可以在运行期间为接口使用底层的ASM字节码增强技术完成代理对象的生产；
 *     因此， 被代理的对象不需要实现任何的接口
 *
 *     可以扩展的用户拦截方法， 主要在Enhancer#setCallback中处理
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
        enhancer.setInterfaces(advisedSupport.getTargetSource().getTargetClass());

        enhancer.setCallback(new DynamicAdvisedInterceptor(advised));
        return enhancer.create();
    }

    private static class DynamicAdvisedInterceptor implements MethodInterceptor {

    }
}
