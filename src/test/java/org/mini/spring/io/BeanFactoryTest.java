package org.mini.spring.io;

import org.junit.Test;
import org.mini.spring.io.service.UserService;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author pp
 * @since 2023/5/15
 */
public class BeanFactoryTest {
    // 初始版的缺点是：
    // 1. bean的创建全部是业务侧自己手动创建出来的， 而不是容器生产出来的。
    // 2.
    @Test
    public void testBeanFactory() {
        /*
        // 1. 完成BeanFactory的初始化
        BeanFactory beanFactory = new BeanFactory();

        // 2. 注册Bean
        BeanDefinition beanDefinition = new BeanDefinition();
        beanDefinition.setBean(new UserService());
        beanFactory.registerBean("userService", beanDefinition);

        // 3. 获取Bean
        Object userService = beanFactory.getBean("userService");
        if (userService instanceof UserService) {
            ((UserService) userService).queryByUserInfo();
        }*/
    }
}
