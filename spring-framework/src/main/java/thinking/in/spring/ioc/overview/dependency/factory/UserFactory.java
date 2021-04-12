package thinking.in.spring.ioc.overview.dependency.factory;

import thinking.in.spring.ioc.overview.dependency.domain.User;

/**
 * 工厂类
 *
 * @author liqiang
 * @date 2021-04-12 下午11:55
 **/
public interface UserFactory {

    default User createUser() {
        return new User(666L, "xxxx");
    }
}
