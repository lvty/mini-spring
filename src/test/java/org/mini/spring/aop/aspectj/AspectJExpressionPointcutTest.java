package org.mini.spring.aop.aspectj;

import org.junit.Test;
import org.mini.spring.beans.service.UserService;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author Administrator
 * @since 2023/7/4
 */
public class AspectJExpressionPointcutTest {

    AspectJExpressionPointcut aspectJExpressionPointcut =
            new AspectJExpressionPointcut("execution(* org.mini.spring.beans.service.UserService.*(..))");

    @Test
    public void matches() throws NoSuchMethodException {
        Class<UserService> clazz = UserService.class;
        Method method = clazz.getDeclaredMethod("queryByUserInfo");

        System.out.println(aspectJExpressionPointcut.matches(clazz)); // true
        System.out.println(aspectJExpressionPointcut.matches(method, clazz)); // true

        // 拦截的方法和对应的对象完全匹配
    }

}