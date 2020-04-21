package ratelimiter;

/**
 * 计数 限流器
 * 简单的限流器，维护一个counter， 每秒清零一次
 *
 * @author Jann Lee
 * @date 2020-04-17 0:47
 **/
public class CounterRateLimiter extends MyRateLimiter {

    public CounterRateLimiter(long permitsPerSecond) {
        super.permitsPerSecond = permitsPerSecond;
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
        counter.set(1);
        timestamp = now;
        return true;
    }
}
