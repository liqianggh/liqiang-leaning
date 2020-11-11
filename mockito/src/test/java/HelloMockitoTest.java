import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Mockito初探
 * <p>
 * 资料：
 * http://ifeve.com/%E4%B8%80%E6%96%87%E8%AE%A9%E4%BD%A0%E5%BF%AB%E9%80%9F%E4%B8%8A%E6%89%8B-mockito-%E5%8D%95%E5%85%83%E6%B5%8B%E8%AF%95%E6%A1%86%E6%9E%B6/
 * https://javadoc.io/static/org.mockito/mockito-core/3.6.0/org/mockito/Mockito.html#stubbing
 */
@RunWith(MockitoJUnitRunner.class)
public class HelloMockitoTest {

    @InjectMocks
    private HelloMockito helloMockito;

    @Mock
    private MockitoService mockitoService;

    @Test
    public void hello() {
        // 1. 当helloMockito内部调用world方法时，会直接返回1；不会执行其内部逻辑
        when(mockitoService.world(anyInt())).thenReturn(1);
        // 2. 结果断言
        assertEquals(helloMockito.hello(), "mockito1");
    }
}