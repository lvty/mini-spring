package org.mini.spring.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.DisposableBean;
import org.mini.spring.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * <p>
 *     销毁方法是适配器,通过对多种销毁方法进行统一的管理，只需要知道其接口定义即可。
 * </p>
 *
 * @author pp
 * @since 2023/5/28
 */
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;

    private final String beanName;

    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        // 1. 判断是否实现初始化接口来实现初始化扩展
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

        // 2. 或者通过配置信息的方式来完成扩展
        if (StrUtil.isNotBlank(destroyMethodName) &&
                !(bean instanceof DisposableBean && "destroy".equals(destroyMethodName))) {
            Method method = bean.getClass().getMethod(destroyMethodName);
            if (null == method) {
                throw new BeansException("Could not find an destroy method names '"
                        + destroyMethodName + "' on bean with name '" + beanName + "'");
            }
            // fixme, 此处是有参初始化方法怎么处理
            method.invoke(bean);
        }
    }
}
