package main.java.cn.mycookies.ratelimiter;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * 限流器测试
 *
 * @author Jann Lee
 * @date 2020-04-17 1:07
 **/
public class RateLimitTest {

    @Test
    public void testCounterRateLimiter() throws InterruptedException {
        MyRateLimiter rateLimiter = new CounterRateLimiter(20);
        while (true) {
            boolean flag = rateLimiter.tryAcquire();
            long seconds = TimeUnit.SECONDS.toSeconds(rateLimiter.timestamp);
            if (flag) {
                System.out.println(seconds + "--------流量被放行--------");
                Thread.sleep(20);
            } else {
                System.out.println(seconds + "流量被限制");
                Thread.sleep((long) (Math.random() * 10 + 1));
            }
        }
    }
}
