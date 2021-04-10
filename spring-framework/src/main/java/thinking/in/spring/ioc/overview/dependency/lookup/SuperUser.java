package thinking.in.spring.ioc.overview.dependency.lookup;

import lombok.Data;
import lombok.ToString;
import thinking.in.spring.annoation.Super;

/**
 * 超级用户
 *
 * @author liqiang
 * @date 2021-04-10 12:07 上午
 **/
@Data
@ToString(callSuper = true)
@Super
public class SuperUser extends User {
    private String address;
}
