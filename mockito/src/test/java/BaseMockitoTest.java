import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * mockito框架单元测试基类
 *
 * @author liqiang
 * @date 2020-11-11 5:01 下午
 **/
@RunWith(MockitoJUnitRunner.class)
public abstract class BaseMockitoTest {
    @BeforeEach
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
    }
}
