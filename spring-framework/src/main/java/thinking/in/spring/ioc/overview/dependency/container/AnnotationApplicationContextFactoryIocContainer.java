package thinking.in.spring.ioc.overview.dependency.container;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import thinking.in.spring.ioc.overview.dependency.domain.User;

import static thinking.in.spring.ioc.overview.dependency.lookup.DependencyLookupDemo.lookupCollectionByType;

/**
 * 注解能力 ApplicationContext 作为 IOC容器示例
 *
 * @author liqiang
 * @date 2021-04-12 上午12:56
 **/
public class AnnotationApplicationContextFactoryIocContainer {

    public static void main(String[] args) {
        // 创建BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AnnotationApplicationContextFactoryIocContainer.class);
        // 启动应用上下文
        applicationContext.refresh();
        // 依赖查找集合对象
        lookupCollectionByType(applicationContext);
    }

    @Bean
    public User user() {
        final User user = new User();
        user.setId(123L);
        user.setName("hello world");
        return user;
    }

}
