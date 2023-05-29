package org.mini.spring.beans.service;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/29
 */
public interface IUserDao {

    /**
     * 通过FactoryBean完成自定义对象的代理动作
     * @param uid
     * @return
     */
    String queryUserName(String uid);
}
