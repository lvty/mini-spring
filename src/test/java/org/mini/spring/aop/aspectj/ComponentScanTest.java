package org.mini.spring.aop.aspectj;

import org.junit.Test;
import org.mini.spring.aop.UService;
import org.mini.spring.context.support.ClassPathXmlApplicationContext;

/**
 * <p>
 *     TODO
 * </p>
 *
 * @author Administrator
 * @since 2023/7/11
 */
public class ComponentScanTest {

    @Test
    public void testComponentScan() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springv14.xml");

        // 获取Bean对象
        Object bean = applicationContext.getBean("u1Service");
        Object bean1 = applicationContext.getBean("uService");
        System.out.println(((UService) bean).queryUserInfo());
        System.out.println(((UService) bean1).queryUserInfo());
    }
}
