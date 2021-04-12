package thinking.in.spring.bean.definition;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import thinking.in.spring.ioc.overview.dependency.domain.User;

/**
 * Bean实例化示例
 * 常规实现方式
 *
 * @author liqiang
 * @date 2021-04-12 下午11:46
 **/
public class BeanInstantiationDemo {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:META-INF/bean-instantiation-context.xml");
        User user = beanFactory.getBean("user-by-static-method", User.class);
        System.out.println(user);

        User user2 = beanFactory.getBean("user-by-instance-method", User.class);
        System.out.println(user2);

        User userBeanFactory = beanFactory.getBean("user-by-factory-bean", User.class);
        System.out.println(userBeanFactory);
    }
}
