package org.mini.spring.beans.service;

import org.mini.spring.beans.factory.DisposableBean;
import org.mini.spring.beans.factory.InitializingBean;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/15
 */
public class UserService implements InitializingBean, DisposableBean {

    private String name;

    /**
     * 新增以下属性，完成对BeanPostProcessor、BeanFactoryPostProcess对Bean信息的扩展处理
     */
    private String company;

    private String location;

    private UserDao userDao;

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
}
