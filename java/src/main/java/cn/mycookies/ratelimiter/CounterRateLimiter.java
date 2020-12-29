package cn.mycookies.ratelimiter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 计数 限流器
 * 简单的限流器，维护一个counter， 每秒清零一次
 *
 * @author Jann Lee
 * @date 2020-04-17 0:47
 **/
public class CounterRateLimiter extends MyRateLimiter {
    /**
     * 计数器
     */
    private final AtomicLong counter = new AtomicLong(0);
    /**
     * 每秒限制请求数
     */
    private final long permitsPerSecond;
    /**
     * 限流器创建时的初始时间
     */
    public long timestamp = System.currentTimeMillis();

    public CounterRateLimiter(long permitsPerSecond) {
        this.permitsPerSecond = permitsPerSecond;
    }

    @Override
    public boolean tryAcquire() {
        long now = System.currentTimeMillis();
        if (now - timestamp < 1000) {
            // 1s窗口内 计数器数量小于限制个数，修改计数器，并返回成功
            if (counter.get() < permitsPerSecond) {
                counter.incrementAndGet();
                return true;
            } else {
                return false;
            }
        }
        // 如果间隔超过1s，则认为下一秒开始，设置为0
        counter.set(0);
        timestamp = now;
        return true;
    }
}
