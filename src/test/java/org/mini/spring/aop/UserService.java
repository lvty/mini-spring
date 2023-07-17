package org.mini.spring.aop;

import org.mini.spring.beans.factory.annotation.Autowired;
import org.mini.spring.beans.factory.annotation.Value;
import org.mini.spring.stereotype.Component;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author Administrator
 * @since 2023/7/4
 */
@Component("userService")
public class UserService implements IUserService {

    @Value("${token}")
    private String token;

    @Autowired
    private UserDao userDao;

    public String queryUserInfo() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "u1" + token + userDao.queryUserName("x");
    }


    public String register(String uName) throws InterruptedException {
        Thread.sleep(100);
        return "registered userName: " + uName;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
