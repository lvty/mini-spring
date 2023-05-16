package org.mini.spring.core.io;

import cn.hutool.core.io.IoUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/16
 */
public class DefaultResourceLoaderTest {

    private DefaultResourceLoader resourceLoader;

    @Before
    public void before(){
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void test_classPath_resource() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:test.properties");
        InputStream inputStream = resource.getInputStream();

        String content = IoUtil.readUtf8(inputStream);
        Assert.assertEquals("a=b", content);
    }

    @Test
    public void test_file_resource() throws IOException {
        Resource resource = resourceLoader.getResource("src/main/resources/test.properties");
        InputStream inputStream = resource.getInputStream();

        String content = IoUtil.readUtf8(inputStream);
        Assert.assertEquals("a=b", content);
    }
}