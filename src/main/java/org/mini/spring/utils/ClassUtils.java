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
}
