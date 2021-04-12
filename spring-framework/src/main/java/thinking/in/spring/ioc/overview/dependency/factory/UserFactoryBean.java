package thinking.in.spring.ioc.overview.dependency.factory;

import org.springframework.beans.factory.FactoryBean;
import thinking.in.spring.ioc.overview.dependency.domain.User;

/**
 * User Bean 的 FactoryBean的实现
 *
 * @author liqiang
 * @date 2021-04-13 上午12:02
 **/
public class UserFactoryBean implements FactoryBean {


    @Override
    public Object getObject() throws Exception {
        return User.createUser();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
