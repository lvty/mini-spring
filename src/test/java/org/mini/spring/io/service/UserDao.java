package org.mini.spring.io.service;

import java.util.UUID;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/16
 */
public class UserDao {

    private String uid;

    private String uName;

    public UserDao(String uid, String uName) {
        this.uid = uid;
        this.uName = uName;
    }

    public UserDao() {
    }

    public String queryUserName() {
        return uName + "->>" + UUID.randomUUID().toString();
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }
}
