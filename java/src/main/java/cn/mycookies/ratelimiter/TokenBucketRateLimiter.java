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
     * 令牌发放速率
     */
    private final long generatedPerSeconds;

    /**
     * 最后一个令牌发放的时间
     */
    long lastTokenTime = System.currentTimeMillis();
    /**
     * 当前令牌数量
     */
    private long currentTokens;

    public TokenBucketRateLimiter(long generatedPerSeconds, int capacity) {
        this.generatedPerSeconds = generatedPerSeconds;
        this.capacity = capacity;
    }

    /**
     * 尝试获取令牌
     *
     * @return true表示获取到令牌，放行；否则为限流
     */
    @Override
    public synchronized boolean tryAcquire() {
        reSync(System.currentTimeMillis());
        if (currentTokens > 0) {
            currentTokens--;
            return true;
        }
        return false;
    }

    /**
     * 请求时间在最后令牌是产生时间之后，则
     * 1. 重新计算令牌桶中的令牌数
     * 2. 将最后一个令牌发放时间重置为当前时间
     *
     * @param now 当前时间
     */
    private synchronized void reSync(long now) {
        if (now - lastTokenTime >= 1000) {
            // 产生新的令牌数
            long newPermits = (now - lastTokenTime) / 1000 * generatedPerSeconds;
            currentTokens = Math.min(currentTokens + newPermits, capacity);
            lastTokenTime = now;
        }
    }

    /**
     * 获取不到就休眠，直到能获取到为止
     */
    public void acquire() {
        long now = System.currentTimeMillis();
        // 预占令牌
        long nextTime = reserve(now);
        long waitTime = nextTime - now;
        if (waitTime > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 预占令牌
     *
     * @param now 当前时间
     * @return 能够获取令牌的时间
     */
    private synchronized long reserve(long now) {
        reSync(now);
        // 能够获取令牌的时间
        long at = lastTokenTime;
        // 本次可以提供的令牌
        long fb = Math.min(1, currentTokens);
        // 令牌净需求：首先减掉令牌桶中的令牌
        long nr = 1 - fb;
        // 重新计算下一个令牌产生时间
        lastTokenTime = lastTokenTime + nr / 1000 * generatedPerSeconds;
        this.currentTokens -= fb;
        // 重新计算
        return at;
    }

}
