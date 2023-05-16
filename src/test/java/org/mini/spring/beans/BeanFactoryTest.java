package org.mini.spring.beans;

import org.junit.Test;
import org.mini.spring.beans.factory.config.BeanDefinition;
import org.mini.spring.beans.factory.config.BeanReference;
import org.mini.spring.beans.factory.support.DefaultListableBeanFactory;
import org.mini.spring.beans.factory.support.XMLBeanDefinitionReader;
import org.mini.spring.beans.service.UserDao;
import org.mini.spring.beans.service.UserService;

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

    @Test
    public void testBeanFactoryV2() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        String beanName = "userService";

        // 注册bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);

        // 首次获取
        Object bean = beanFactory.getBean(beanName);
        if(bean instanceof UserService){
            ((UserService) bean).queryByUserInfo();
        }

        Object bean1 = beanFactory.getBean(beanName);
        if(bean1 instanceof UserService){
            ((UserService) bean1).queryByUserInfo();
        }

        System.out.println(bean == bean1);

    }

    @Test
    public void testBeanFactoryV3() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        String beanName = "userService";

        // 注册bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition(beanName, beanDefinition);

        // 首次获取
        Object bean = beanFactory.getBean(beanName, "haha");
        if(bean instanceof UserService){
            ((UserService) bean).queryByUserInfo();
        }

        Object bean1 = beanFactory.getBean(beanName);
        if(bean1 instanceof UserService){
            ((UserService) bean1).queryByUserInfo();
        }

        System.out.println(bean == bean1);

        System.out.println(bean);

    }

    @Test
    public void testBeanFactoryV4() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 注册UserDao
        beanFactory.registerBeanDefinition("uDao", new BeanDefinition(UserDao.class,
                new PropertyValues()
                        .addPropertyValue(new PropertyValue("uid", "pp"))
                        .addPropertyValue(new PropertyValue("uName", "pp"))
        ));

        // 注册UserService
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name", "haha"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("uDao")));

        // 注入
        String beanName = "userService";
        beanFactory.registerBeanDefinition(beanName, new BeanDefinition(UserService.class, propertyValues));

        Object bean = beanFactory.getBean(beanName);
        if(bean instanceof UserService){
            ((UserService) bean).queryByUserInfo();
        }

        System.out.println(bean);

    }

    @Test
    public void testBeanFactoryV5() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 读取配置文件并注册
        XMLBeanDefinitionReader xmlBeanDefinitionReader = new XMLBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        // 获取对象
        Object bean = beanFactory.getBean("userService");
        if(bean instanceof UserService){
            ((UserService) bean).queryByUserInfo();
        }

        System.out.println(bean);

    }
}
