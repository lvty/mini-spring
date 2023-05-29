package org.mini.spring.beans.factory.support;

import org.mini.spring.beans.factory.DisposableBean;
import org.mini.spring.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 单例注册接口的默认实现
 * </p>
 *
 * @author pp
 * @since 2023/5/15
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    protected final Object NULL_OBJECT = null;

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    private Map<String, Object> singletonObjects
            = new HashMap<>();

    /**
     * 需要销毁的全部方法
     */
    private Map<String, DisposableBean> disposableBeans = new HashMap<>();

    /**
     * 可以被继承此类的其他类调用
     *
     * @param name
     * @param singletonObject
     */
    protected void addSingleton(String name, Object singletonObject) {
        singletonObjects.put(name, singletonObject);
    }

    protected void registerDisposableBean(String name, DisposableBean disposableBean) {
        disposableBeans.put(name, disposableBean);
    }

    public void destroySingletons() throws Exception {
        for (DisposableBean disposableBean : disposableBeans.values()) {
            disposableBean.destroy();
        }
    }
}
