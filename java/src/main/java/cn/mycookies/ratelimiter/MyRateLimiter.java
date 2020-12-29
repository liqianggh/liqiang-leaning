package cn.mycookies.ratelimiter;

/**
 * 限流器
 *
 * @author Jann Lee
 * @date 2020-04-17 0:26
 **/
public abstract class MyRateLimiter {

    /**
     * 尝试获取流量
     *
     * @return true 表示当前流量可以放行，否则表示拒绝
     */
    public abstract boolean tryAcquire();

}
