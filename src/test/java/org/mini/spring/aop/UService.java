package org.mini.spring.aop;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author Administrator
 * @since 2023/7/4
 */
public class UService implements IUserService {

    public String queryUserInfo() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "u1";
    }


    public String register(String uName) throws InterruptedException {
        Thread.sleep(100);
        return "registered userName: " + uName;
    }


}
