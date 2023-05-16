package org.mini.spring.io.service;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/15
 */
public class UserService {

    private String name;

    private UserDao userDao;

    public UserService(String name) {
        this.name = name;
    }

    public UserService() {
    }

    public void queryByUserInfo(){
        System.out.println("user info: " + userDao.queryUserName());
    }

    @Override
    public String toString() {
        return "UserService{" +
                "name='" + name + '\'' +
                '}';
    }
}
