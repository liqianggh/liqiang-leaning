import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.verify;

public class MockitoTest extends BaseMockitoTest {
    @Mock
    private List<String> mockList;

    @Test
    public void verify_SimpleInvocationOnMock() {
        mockList.size();
        verify(mockList).size();
    }

}