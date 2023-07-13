package org.mini.spring.utils;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/16
 */
public class ClassUtils {


    public static ClassLoader getDefaultClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static boolean isCglibProxyClass(String className) {
        return (className != null && className.contains("$$"));
    }

    public static boolean isCglibProxyClass(Class<?> clazz) {
        return isCglibProxyClass(clazz.getName());
    }
}
