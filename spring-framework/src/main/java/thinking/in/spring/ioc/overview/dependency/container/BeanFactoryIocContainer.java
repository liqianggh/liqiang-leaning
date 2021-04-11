package thinking.in.spring.ioc.overview.dependency.container;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import static thinking.in.spring.ioc.overview.dependency.lookup.DependencyLookupDemo.lookupCollectionByType;

/**
 * BeanFactory作为 IOC容器示例
 *
 * @author liqiang
 * @date 2021-04-12 上午12:56
 **/
public class BeanFactoryIocContainer {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        int beanDefinitionCount = reader.loadBeanDefinitions("classpath:dependency-lookup-context.xml");
        System.out.println("Bean定义加载的数量：" + beanDefinitionCount);

        // 依赖查找集合对象
        lookupCollectionByType(beanFactory);
    }

}
