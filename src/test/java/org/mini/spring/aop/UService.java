package org.mini.spring.aop;

import org.mini.spring.stereotype.Component;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author Administrator
 * @since 2023/7/4
 */
@Component("uService")
public class UService implements IUserService {

    private String token;

    public String queryUserInfo() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "u1" + token;
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
