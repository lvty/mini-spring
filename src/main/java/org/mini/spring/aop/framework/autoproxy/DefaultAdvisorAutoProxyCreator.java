package org.mini.spring.aop.framework.autoproxy;

import org.mini.spring.aop.AdvisedSupport;
import org.mini.spring.aop.ClassFilter;
import org.mini.spring.aop.TargetSource;
import org.mini.spring.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.mini.spring.aop.framework.ProxyFactory;
import org.mini.spring.aop.intercept.MethodInterceptor;
import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.PropertyValues;
import org.mini.spring.beans.factory.BeanFactory;
import org.mini.spring.beans.factory.BeanFactoryAware;
import org.mini.spring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.mini.spring.beans.factory.support.DefaultListableBeanFactory;

import java.util.Collection;

/**
 * <p>
 *     从通过 beanFactory.getBeansOfType 获取 AspectJExpressionPointcutAdvisor 开始。
 *
 *     获取了 advisors 以后就可以遍历相应的 AspectJExpressionPointcutAdvisor 填充对
 *     应的属性信息，包括：目标对象、拦截方法、匹配器，之后返回代理对象即可。
 *
 *     那么现在调用方获取到的这个 Bean 对象就是一个已经被切面注入的对象了，当调用方法的时候，
 *     则会被按需拦截，处理用户需要的信息。
 *
 * </p>
 *
 * @author Administrator
 * @since 2023/7/5
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private DefaultListableBeanFactory beanFactory;
    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }


    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) {
        Collection advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();

        for(Object advisor: advisors){
            AspectJExpressionPointcutAdvisor advisor_ = (AspectJExpressionPointcutAdvisor) advisor;

            ClassFilter classFilter = advisor_.getPointcut().getClassFilter();
            if(!classFilter.matches(beanClass)) continue;

            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource;
            try {
                targetSource = new TargetSource(beanClass.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            //获取了 advisors 以后就可以遍历相应的 AspectJExpressionPointcutAdvisor 填充对
            //应的属性信息，包括：目标对象、拦截方法、匹配器，之后返回代理对象即可。

            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor_.getAdvice());
            advisedSupport.setMethodMatcher(advisor_.getPointcut().getMethodMatcher());
            advisedSupport.setProxyTargetClass(false);

            return new ProxyFactory(advisedSupport).getProxy();

        }

        return null;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, Object bean, String beanName) {
        return null;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }
}
