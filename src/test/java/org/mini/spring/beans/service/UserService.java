package org.mini.spring.beans.service;

import org.mini.spring.beans.factory.*;
import org.mini.spring.context.ApplicationContext;
import org.mini.spring.context.ApplicationContextAware;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/15
 */
public class UserService implements InitializingBean, DisposableBean,
        BeanNameAware, BeanClassLoaderAware, ApplicationContextAware, BeanFactoryAware {


    private String name;

    /**
     * 新增以下属性，完成对BeanPostProcessor、BeanFactoryPostProcess对Bean信息的扩展处理
     */
    private String company;

    private String location;

    private UserDao userDao;

    private IUserDao iUserDao;


    public String queryUserInfo(){
        return iUserDao.queryUserName(name) + "," + company + "," + location;
    }

    public UserService(String name) {
        this.name = name;
    }

    public UserService() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void queryByUserInfo(){
        System.out.println("user info: " + userDao.queryUserName());
    }

    @Override
    public String toString() {
        return "UserService{" +
                "name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", userDao=" + userDao +
                '}';
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("execute userService destroy.");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("execute UserService afterPropertiesSet.");
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("Aware classLoader: " + classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        System.out.println("Aware beanFactory: " + beanFactory);
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("Aware beanName: " + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        System.out.println("Aware applicationContext: " + applicationContext);
    }
}
