package cn.mycookies.ratelimiter;

/**
 * 漏桶限流器
 * 桶的容量固定，当桶的容量满时
 * 以恒定速率从桶中消耗令牌
 *
 * @author Jann Lee
 * @date 2020-04-17 1:19
 **/
public class LeakyBucketRateLimiter extends MyRateLimiter {
    // 桶的容量
    private final int capacity;
    // 漏出速率
    private final int permitsPerSecond;
    // 剩余水量
    private long leftWater;
    // 上次注入时间
    private long timeStamp = System.currentTimeMillis();

    public LeakyBucketRateLimiter(int permitsPerSecond, int capacity) {
        this.capacity = capacity;
        this.permitsPerSecond = permitsPerSecond;
    }

    @Override
    public synchronized boolean tryAcquire() {
        //1. 计算剩余水量
        long now = System.currentTimeMillis();
        long timeGap = (now - timeStamp) / 1000;
        leftWater = Math.max(0, leftWater - timeGap * permitsPerSecond);
        timeStamp = now;

        // 如果未满，则放行；否则限流
        if (leftWater < capacity) {
            leftWater += 1;
            return true;
        }
        return false;
    }
}
