package thinking.in.spring.ioc.overview.dependency.lookup;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import thinking.in.spring.ioc.overview.dependency.annoation.Super;
import thinking.in.spring.ioc.overview.dependency.domain.User;

import java.util.Map;


/**
 * 依赖查找实例
 *
 * @author liqiang
 * @date 2021-04-09 12:47 下午
 **/
public class DependencyLookupDemo {
    public static void main(String[] args) {
        // 1. 根据Bean名称查询-实时查询
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:META-INF/dependency-lookup-context.xml");
        lookupByNameRealTime(beanFactory);

        // 2. 根据Bean名称查询-延时查询
        lookupByNameLazy(beanFactory);

        // 3.1 按照类型查找；单个bean
        lookupByTypeRealTime(beanFactory);
        // 3.2 集合bean
        lookupCollectionByType(beanFactory);

        // 4. 根据注解获取
        lookupByAnnotation(beanFactory);
    }

    private static void lookupByNameRealTime(BeanFactory beanFactory) {
        User user = (User) beanFactory.getBean("user");
        System.out.println(user);
    }

    private static void lookupByNameLazy(BeanFactory beanFactory) {
        ObjectFactory<User> user = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        System.out.println(user.getObject());
    }

    private static void lookupByTypeRealTime(BeanFactory beanFactory) {
        User user = beanFactory.getBean(User.class);
        System.out.println(user);
    }

    public static void lookupCollectionByType(BeanFactory beanFactory) {
        User user;
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> beansOfType = listableBeanFactory.getBeansOfType(User.class);
            user = beansOfType.get("user");
        } else {
            user = beanFactory.getBean(User.class);
        }
        System.out.println(user);
    }

    private static void lookupByAnnotation(BeanFactory beanFactory) {
        User user;
        if (beanFactory instanceof ListableBeanFactory) {
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> beansOfAnnotation = (Map) listableBeanFactory.getBeansWithAnnotation(Super.class);
            user = beansOfAnnotation.get("superUser");
        } else {
            user = beanFactory.getBean(User.class);
        }
        System.out.println(user);
    }
}
