package thinking.in.spring.ioc.overview.dependency.injection;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.Bean;
import thinking.in.spring.ioc.overview.dependency.domain.User;

/**
 * 基于XML资源的依赖Setter方法注入示例
 *
 * @author liqiang
 * @date 2021-04-22 上午12:50
 **/
public class XmlDependencySetterInjectionDemo {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        String xmlResourcePath = "classpath:/META-INF/dependency-injection-context.xml";
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

        final UserHolder bean = beanFactory.getBean(UserHolder.class);
        System.out.println(bean);

    }

    /**
     * 注解方式注入
     */
    @Bean
    public UserHolder userHolder(User user) {
        return new UserHolder(user);
    }
}
