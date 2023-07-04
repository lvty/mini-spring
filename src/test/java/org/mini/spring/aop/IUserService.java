package org.mini.spring.aop;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author Administrator
 * @since 2023/7/4
 */
public interface IUserService {


    String queryUserInfo();

    String register(String uName) throws InterruptedException;
}
