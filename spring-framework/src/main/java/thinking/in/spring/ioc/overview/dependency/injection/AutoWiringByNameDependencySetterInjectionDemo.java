package thinking.in.spring.ioc.overview.dependency.injection;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 基于AutoWiring byName 方式注入示例
 *
 * @author liqiang
 * @date 2021-04-22 上午12:50
 **/
public class AutoWiringByNameDependencySetterInjectionDemo {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String xmlResourcePath = "classpath:/META-INF/autowiring-dependency-injection-context.xml";
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

        final UserHolder bean = beanFactory.getBean(UserHolder.class);
        System.out.println(bean);
    }

}
