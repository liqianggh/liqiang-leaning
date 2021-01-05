package cn.mycookies.ratelimiter;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 限流器测试
 *
 * @author Jann Lee
 * @date 2020-04-17 1:07
 **/
public class RateLimitTest {

    @Test
    public void testCounterRateLimiter() throws InterruptedException {
        CounterRateLimiter rateLimiter = new CounterRateLimiter(20);
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


    @Test
    public void testTokenBucketRateLimiter() {
        TokenBucketRateLimiter rateLimiter = new TokenBucketRateLimiter(1, 20);
        AtomicLong counter = new AtomicLong(0);
        while (true) {
            rateLimiter.acquire();
            counter.getAndAdd(1);
            System.out.println(counter.get() + "--------流量被放行--------" + System.currentTimeMillis());
        }
    }

    @Test
    public void testTokenBucketRateLimiter2() throws InterruptedException {
        TokenBucketRateLimiter rateLimiter = new TokenBucketRateLimiter(100, 20);
        AtomicLong counter = new AtomicLong(0);
        while (true) {
            boolean flag = rateLimiter.tryAcquire();
            long seconds = System.currentTimeMillis();
            if (flag) {
                System.out.println(seconds + "--------流量被放行--------");
            } else {
                System.out.println(seconds + "流量被限制");
            }
        }
    }

    @Test
    public void testLeakyBucketRateLimiter() throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        ExecutorService singleThread = Executors.newSingleThreadExecutor();

        LeakyBucketRateLimiter rateLimiter = new LeakyBucketRateLimiter(20, 20);
        // 存储流量的队列
        Queue<Integer> queue = new LinkedList<>();
        // 模拟请求  不确定速率注水
        singleThread.execute(() -> {
            int count = 0;
            while (true) {
                count++;
                boolean flag = rateLimiter.tryAcquire();
                if (flag) {
                    queue.offer(count);
                    System.out.println(count + "--------流量被放行--------");
                } else {
                    System.out.println(count + "流量被限制");
                }
                try {
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        // 模拟处理请求 固定速率漏水
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            if (!queue.isEmpty()) {
                System.out.println(queue.poll() + "被处理");
            }
        }, 0, 100, TimeUnit.MILLISECONDS);

        // 保证主线程不会退出
        while (true) {
            Thread.sleep(10000);
        }
    }


    @Test
    public void testSlidingRateLimiter() throws InterruptedException {
        SlidingWindowRateLimiter rateLimiter = new SlidingWindowRateLimiter(60);
        while (true) {
            boolean flag = rateLimiter.tryAcquire();
            long seconds = System.currentTimeMillis() / 1000;
            if (flag) {
                System.out.println(seconds + "--------流量被放行--------");
                Thread.sleep((long) (Math.random() * 2000));
            } else {
                System.out.println(seconds + "流量被限制");
            }
        }
    }
}
