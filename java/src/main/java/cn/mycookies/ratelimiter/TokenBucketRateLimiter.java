package cn.mycookies.ratelimiter;

import java.util.concurrent.TimeUnit;

/**
 * 令牌桶限流器
 * 以恒定的速率生成令牌，当令牌桶满时无法在生成令牌
 * 当令牌桶中无令牌时，只能按照令牌生成速率放放流
 *
 * @author Jann Lee
 * @date 2020-04-17 1:19
 **/
public class TokenBucketRateLimiter extends MyRateLimiter {
    /**
     * 令牌桶的容量「限流器允许的最大突发流量」
     */
    private final long capacity;

    /**
     * 令牌发放时间间隔
     */
    private final long permitsPerSeconds;
    /**
     * 下一个令牌产生时间
     */
    long next = System.currentTimeMillis();
    /**
     * 当前令牌数量
     */
    private long currentTokens;


    public TokenBucketRateLimiter(long permitsPerSeconds, int capacity) {
        this.permitsPerSeconds = permitsPerSeconds;
        this.capacity = capacity;
    }

    @Override
    public boolean tryAcquire() {
        long now = System.currentTimeMillis();
        // 预占令牌
        long waitTime = reserve(now);
        if (waitTime > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 请求时间在下一个令牌是产生时间之后，则
     * 1. 重新计算令牌桶中的令牌数
     * 2. 将下一个令牌发放时间重置为当前时间
     *
     * @param now 当前时间
     */
    private void reSync(long now) {
        if (now > next) {
            // 产生新的令牌数
            long newPermits = (now - next) / 1000 / permitsPerSeconds;
            currentTokens = Math.min(currentTokens + newPermits, capacity);
            next = now;
        }
    }

    /**
     * 预占令牌
     *
     * @param now 当前时间
     * @return 能够获取令牌需要等待的时间
     */
    private synchronized long reserve(long now) {
        reSync(now);
        // 能够获取令牌的时间
        long at = next;
        // 本次可以提供的令牌
        long fb = Math.min(1, currentTokens);
        // 令牌净需求：首先减掉令牌桶中的令牌
        long nr = 1 - fb;
        // 重新计算下一个令牌产生时间
        next = next + nr * permitsPerSeconds * 1000;
        // 重新计算
        return Math.min(at - now, 0);
    }
}
