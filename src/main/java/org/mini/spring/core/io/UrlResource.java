package org.mini.spring.core.io;

import cn.hutool.core.lang.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * <p>
 * 通过Http的方式读取云文件
 * </p>
 *
 * @author pp
 * @since 2023/5/16
 */
public class UrlResource implements Resource {

    private final URL url;

    public UrlResource(URL url) {
        Assert.notNull(url, "URL must not be null");
        this.url = url;
    }


    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection urlConnection = this.url.openConnection();
        try (InputStream in = urlConnection.getInputStream()) {
            return in;
        }
    }
}
