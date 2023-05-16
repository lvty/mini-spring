package org.mini.spring.core.io;

/**
 * <p>
 *     完成资源的加载， 依赖Resource
 *     统一完成对不同的资源加载形式进行处理，外部用户只需要传递资源的地址即可
 * </p>
 *
 * @author pp
 * @since 2023/5/16
 */
public interface ResourceLoader {

    String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 资源的定位信息
     * @param location
     * @return
     */
    Resource getResource(String location);
}
