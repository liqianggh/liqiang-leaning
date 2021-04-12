package thinking.in.spring.bean.definition;

import lombok.Data;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import thinking.in.spring.ioc.overview.dependency.domain.User;

import java.util.Map;

/**
 * 注解 BeanDefinition示例
 *
 * @author liqiang
 * @date 2021-04-12 下午11:25
 **/
@Import(AnnotationBeanDefinitionDemo.Config.class)
public class AnnotationBeanDefinitionDemo {
    public static void main(String[] args) {
        // 创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册Configuration class 配置类
        applicationContext.register(Config.class);
        // 启动Spring上下文
        applicationContext.refresh();

        // 1. 通过@Bean方式定义
        // 2. 通过@Component方式定义
        // 3. 通过@Import进行导入
        final Map<String, Config> beansOfType = applicationContext.getBeansOfType(Config.class);
        System.out.println(beansOfType);
        System.out.println(applicationContext.getBean("user"));
        // 关闭上下文
        applicationContext.close();
    }


    @Component
    @Data
    public static class Config {
        @Bean(name = {"user", "javaTKBJ-user"})
        public User user() {
            return new User(1L, "halo");
        }
    }
}
