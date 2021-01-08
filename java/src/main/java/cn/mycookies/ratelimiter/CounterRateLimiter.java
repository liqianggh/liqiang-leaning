package cn.mycookies.ratelimiter;

/**
 * 计数 限流器
 * 简单的限流器，维护一个counter， 每秒清零一次
 *
 * @author Jann Lee
 * @date 2020-04-17 0:47
 **/
public class CounterRateLimiter extends MyRateLimiter {
    /**
     * 每秒限制请求数
     */
    private final long permitsPerSecond;
    /**
     * 上一个窗口的开始时间
     */
    public long timestamp = System.currentTimeMillis();
    /**
     * 计数器
     */
    private int counter;

    public CounterRateLimiter(long permitsPerSecond) {
        this.permitsPerSecond = permitsPerSecond;
    }

    @Override
    public synchronized boolean tryAcquire() {
        long now = System.currentTimeMillis();
        // 窗口内请求数量小于阈值，更新计数放行，否则拒绝请求
        if (now - timestamp < 1000) {
            if (counter < permitsPerSecond) {
                counter++;
                return true;
            } else {
                return false;
            }
        }
        // 时间窗口过期，重置计数器和时间戳
        counter = 0;
        timestamp = now;
        return true;
    }
}
