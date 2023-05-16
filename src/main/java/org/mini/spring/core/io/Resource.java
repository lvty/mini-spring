package org.mini.spring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 *     定义各类资源， 主要包含： ClassPathResource/FileSystemResource/UrlResource
 * </p>
 *
 * @author pp
 * @since 2023/5/16
 */
public interface Resource {

    /**
     * 处理资源加载流
     * @return
     * @throws IOException
     */
    InputStream getInputStream() throws IOException;
}
