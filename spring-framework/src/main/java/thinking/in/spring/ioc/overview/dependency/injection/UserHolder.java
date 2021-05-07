package thinking.in.spring.ioc.overview.dependency.injection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import thinking.in.spring.ioc.overview.dependency.domain.User;

/**
 * 测试setter方法注入用
 *
 * @author liqiang
 * @date 2021-04-22 上午12:54
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserHolder {
    private User user;
}
