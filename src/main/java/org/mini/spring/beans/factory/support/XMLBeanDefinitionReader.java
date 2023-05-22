package org.mini.spring.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import org.mini.spring.beans.BeansException;
import org.mini.spring.beans.PropertyValue;
import org.mini.spring.beans.factory.config.BeanDefinition;
import org.mini.spring.beans.factory.config.BeanReference;
import org.mini.spring.core.io.Resource;
import org.mini.spring.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 最终实现类只需要关心具体的业务实现：
 * DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
 *
 *         // 读取配置文件并注册
 *         XMLBeanDefinitionReader xmlBeanDefinitionReader = new XMLBeanDefinitionReader(beanFactory);
 *         xmlBeanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");
 *
 *         // 获取对象
 *         Object bean = beanFactory.getBean("xx");
 *         if(bean instanceof UserService){
 *             ((UserService) bean).doSomething();
 *         }
 *
 *
 * </p>
 *
 * @author pp
 * @since 2023/5/16
 */
public class XMLBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XMLBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XMLBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try (InputStream in = resource.getInputStream()) {
            doLoadBeanDefinitions(in);
        } catch (IOException | ClassNotFoundException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    /**
     * 根据指定的流完成资源的加载: 获取Bean的配置信息；
     *  id /name/class
     *      property name/value/ref
     *
     * @param in
     */
    protected void doLoadBeanDefinitions(InputStream in) throws ClassNotFoundException {
        Document document = XmlUtil.readXML(in);
        // 文档根元素
        Element root = document.getDocumentElement();

        // 子元素
        NodeList childNodes = root.getChildNodes();

        // 遍历
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);

            // 非元素类型， 直接下一个
            if (!(item instanceof Element)) continue;

            // 非Bean配置， 继续下一个
            if (!"bean".equals(item.getNodeName())) continue;

            // 解析标签内容
            Element bean = (Element) item;

            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");

            // 获取Class
            // fixme: 此处要求， Class属性必须要配置起来
            Class<?> clazz = Class.forName(className);

            // 优先级id > name
            String beanName = StrUtil.isNotEmpty(id) ? id : name;

            if (StrUtil.isBlank(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            // 定义Bean
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            // 读取属性并完成属性定义
            NodeList cn = item.getChildNodes();
            for (int j = 0; j < cn.getLength(); j++) {

                Node node = cn.item(j);
                if (!(node instanceof Element)) continue;

                // 限制标签名称
                if (!"property".equals(node.getNodeName())) continue;

                Element property = (Element) node;

                String attrName = property.getAttribute("name");
                String attrValue = property.getAttribute("value");
                String attrRef = property.getAttribute("ref");

                // 获取属性值，判断对应的类型
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;

                // 创建属性信息
                PropertyValue propertyValue = new PropertyValue(attrName, value);

                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }

            if(getRegistry().containsBeanDefinition(beanName)){
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }

            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resource) throws BeansException {
        for (Resource re : resource) {
            loadBeanDefinitions(re);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for(String location: locations){
            loadBeanDefinitions(location);
        }
    }
}
