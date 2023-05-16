package org.mini.spring.core.io;

import cn.hutool.core.lang.Assert;
import org.mini.spring.utils.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 使用ClassLoader读取ClassPath下的文件信息
 * </p>
 *
 * @author pp
 * @since 2023/5/16
 */
public class ClassPathResource implements Resource {

    private final String path;

    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, (ClassLoader) null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        Assert.notNull(path, "Path must not be null");
        this.path = path;
        this.classLoader = classLoader == null ? ClassUtils.getDefaultClassLoader() : classLoader;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream in = classLoader.getResourceAsStream(path);
        if (in == null) {
            throw new FileNotFoundException(this.path + " cannot be opened because it does not exist.");
        }
        return in;
    }
}
