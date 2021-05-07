package thinking.in.spring.ioc.overview.dependency.injection;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import thinking.in.spring.ioc.overview.dependency.domain.User;

/**
 * 基于注解的注入示例
 *
 * @author liqiang
 * @date 2021-04-22 上午12:50
 **/
public class AnnotationDependencySetterInjectionDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        applicationContext.register(AnnotationDependencySetterInjectionDemo.class);
        beanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");
        applicationContext.refresh();
        final UserHolder bean = applicationContext.getBean(UserHolder.class);
        System.out.println(bean);
        applicationContext.close();
    }

    /**
     * 注解方式注入
     */
    @Bean
    public UserHolder userHolder(User user) {
        return new UserHolder(user);
    }

}
