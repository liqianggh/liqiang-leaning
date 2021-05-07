package thinking.in.spring.ioc.overview.dependency.lookup;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 通过 ObjectProvider进行依赖查找
 *
 * @author liqiang
 * @date 2021-04-13 下午11:29
 **/
public class ObjectProviderDemo {

    public static void main(String[] args) {
        // 创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 将当前类作为配置类
        applicationContext.register(ObjectProviderDemo.class);
        // 启动应用上下文
        applicationContext.refresh();

        lookupByObjectProvider(applicationContext);
        // 关闭上应用上下文
        applicationContext.close();
    }

    public static void lookupByObjectProvider(AnnotationConfigApplicationContext configApplicationContext) {
        final ObjectProvider<String> beanProvider = configApplicationContext.getBeanProvider(String.class);
        System.out.println(beanProvider.getObject());
    }

    /**
     * bean名称就是方法名称
     */
    @Bean
    public String helloWorld() {
        return "Hello world";
    }
}
