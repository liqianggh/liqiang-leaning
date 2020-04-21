package main.java.cn.mycookies.ratelimiter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 限流器
 *
 * @author Jann Lee
 * @date 2020-04-17 0:26
 **/
public abstract class MyRateLimiter {

    /**
     * 计数器
     */
    AtomicLong counter = new AtomicLong();

    /**
     * 每秒限制请求数
     */
    long permitsPerSecond;

    /**
     * 限流器创建时的初始时间
     */
    long timestamp = System.currentTimeMillis();

    /**
     * 尝试获取流量
     *
     * @return true 表示当前流量可以放行，否则表示拒绝
     */
    public abstract boolean tryAcquire();

}
