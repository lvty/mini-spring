package org.mini.spring.beans.factory.support;

import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *     关于FactoryBean 此类对象的注册操作
 *     放到单独的类里面，希望做到不同领域模块下只负责各自需要完成的功能， 避免因为扩展导致的类膨胀到难以维护。
 * </p>
 *
 * @author pp
 * @since 2023/5/29
 */
public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    /**
     * cache of singleton objects created nby factoryBeans
     */
    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();


    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object o = factoryBeanObjectCache.get(beanName);
        return o != NULL_OBJECT ? o : null;
    }

    protected Object getObjectFromFactoryBean(FactoryBean factoryBean, String beanName) {
        if (factoryBean.isSingleton()) {
            Object obj = factoryBeanObjectCache.get(beanName);
            if (null == obj) {
                obj = doGetObjectFromFactoryBean(factoryBean, beanName);
                this.factoryBeanObjectCache.put(beanName, obj);
            }
            return obj;
        } else {
            return doGetObjectFromFactoryBean(factoryBean, beanName);
        }
    }

    private Object doGetObjectFromFactoryBean(FactoryBean factoryBean, String name) {
        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean throw exception on object ['" + name + "]'", e);
        }
    }


}
