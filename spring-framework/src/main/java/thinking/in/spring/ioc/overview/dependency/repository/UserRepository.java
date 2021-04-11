package thinking.in.spring.ioc.overview.dependency.repository;

import lombok.Data;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import thinking.in.spring.ioc.overview.dependency.domain.User;

import java.util.Collection;


/**
 * 测试用
 *
 * @author liqiang
 * @date 2021-04-12 上午12:22
 **/
@Data
public class UserRepository {

    private Collection<User> users;

    private BeanFactory beanFactory;

    private ObjectFactory<ApplicationContext> objectFactory;

}
