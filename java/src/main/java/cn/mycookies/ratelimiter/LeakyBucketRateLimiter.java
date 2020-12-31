package cn.mycookies.ratelimiter;

/**
 * 漏桶限流器
 *
 * @author Jann Lee
 * @date 2020-04-17 1:19
 **/
public class LeakyBucketRateLimiter extends MyRateLimiter {

    public LeakyBucketRateLimiter(long permitsPerSecond) {
    }

    @Override
    public boolean tryAcquire() {

        return false;
    }
}
