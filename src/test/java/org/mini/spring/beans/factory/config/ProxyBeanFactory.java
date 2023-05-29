package org.mini.spring.beans.factory.config;

import org.mini.spring.beans.factory.FactoryBean;
import org.mini.spring.beans.service.IUserDao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/29
 */
public class ProxyBeanFactory implements FactoryBean<IUserDao> {
    @Override
    public IUserDao getObject() throws Exception {
        // 当有方法被调用的时候， 执行的是代理对象的功能
        InvocationHandler handler = (proxy, method, args) -> "被代理了";

        return (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
                , new Class[]{IUserDao.class}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IUserDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
