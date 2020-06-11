package cn.mycookies.ratelimiter;

/**
 * 滑动窗口限流
 * 可以理解为是{@link CounterRateLimiter} 的升级版本
 * 解决其在1s窗口切换时，流量峰值可能时限制值的两倍问题
 *
 * @author Jann Lee
 * @date 2020-04-17 1:00
 **/
public class SlidingWindowRateLimiter extends MyRateLimiter {

    @Override
    public boolean tryAcquire() {
        return false;
    }
}
