package thinking.in.spring.bean.definition;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import thinking.in.spring.ioc.overview.dependency.factory.UserFactory;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 特殊实现方式 SPI机制  TODO
 *
 * @author liqiang
 * @date 2021-04-13 上午12:48
 **/
public class SpecialBeanInstantiationDemo {
    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:META-INF/special-instantiation-context.xml");
//        final ServiceLoader serviceLoader = beanFactory.getBean("userFactoryServiceLoader", ServiceLoader.class);
//        Iterator<UserFactory> iterator = serviceLoader.iterator();
//        while (iterator.hasNext()) {
//            UserFactory userFactory = iterator.next();
//            System.out.println(userFactory.createUser());
//        }

        demoServiceLoader();
    }

    public static void demoServiceLoader() {
        ServiceLoader<UserFactory> serviceLoader = ServiceLoader.load(UserFactory.class, Thread.currentThread().getContextClassLoader());
        Iterator<UserFactory> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            UserFactory userFactory = iterator.next();
            System.out.println(userFactory.createUser());
        }

    }
}
