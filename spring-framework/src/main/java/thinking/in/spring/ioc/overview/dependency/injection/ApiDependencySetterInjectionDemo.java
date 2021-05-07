package thinking.in.spring.ioc.overview.dependency.injection;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 基于Java APi方法注入示例
 *
 * @author liqiang
 * @date 2021-04-22 上午12:50
 **/
public class ApiDependencySetterInjectionDemo {
    public static void main(String[] args) {
        // 创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册UserHolder的BeanDefinition
        applicationContext.registerBeanDefinition("userHolder", createUserHolderBeanDefinition());


        // 引入xml配置
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        beanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/dependency-lookup-context.xml");

        // 启动上下文
        applicationContext.refresh();
        final UserHolder bean = applicationContext.getBean(UserHolder.class);
        System.out.println(bean);
        applicationContext.close();
    }

    /**
     * 为UserHolder生成BeanDefinition
     */
    private static BeanDefinition createUserHolderBeanDefinition() {
        final BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(UserHolder.class);
        beanDefinitionBuilder.addPropertyReference("user", "superUser");
        return beanDefinitionBuilder.getBeanDefinition();
    }
}
