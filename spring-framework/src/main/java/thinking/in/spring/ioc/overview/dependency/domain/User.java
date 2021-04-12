package thinking.in.spring.ioc.overview.dependency.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户类
 *
 * @author liqiang
 * @date 2021-04-09 12:48 下午
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String name;
}
