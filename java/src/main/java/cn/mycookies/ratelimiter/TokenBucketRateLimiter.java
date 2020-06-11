package cn.mycookies.ratelimiter;

/**
 * 令牌桶限流器
 *
 * @author Jann Lee
 * @date 2020-04-17 1:19
 **/
public class TokenBucketRateLimiter extends MyRateLimiter {

    public TokenBucketRateLimiter(long permitsPerSecond) {
        super.permitsPerSecond = permitsPerSecond;
    }

    @Override
    public boolean tryAcquire() {

        return false;
    }
}
