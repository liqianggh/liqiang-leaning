import org.apache.commons.lang3.RandomUtils;


/**
 * hello mockito
 *
 * @author liqiang
 * @date 2020-11-11 3:40 下午
 **/
public class HelloMockito {

    MockitoService mockitoService = new MockitoService();

    public String hello() {
        return "mockito" + mockitoService.world(RandomUtils.nextInt());
    }

}
