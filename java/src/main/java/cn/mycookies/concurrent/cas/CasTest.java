package cn.mycookies.concurrent.cas;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * cas对比加锁测试
 *
 * @author Jann Lee
 * @date 2019-11-21 0:12
 **/
public class CasTest {

    @Test
    public void test() {
        long times = 500_000_000;
        // 记录耗时
        List<Long> elapsedTime4NoLock = new ArrayList<>(10);
        List<Long> elapsedTime4Synchronized = new ArrayList<>(10);
        List<Long> elapsedTime4ReentrantLock = new ArrayList<>(10);
        List<Long> elapsedTime4Cas = new ArrayList<>(10);

        // 进行10组试验
        for (int j = 0; j < 10; j++) {
            // 无锁
            long startTime = System.currentTimeMillis();
            for (long i = 0; i < times; i++) {
            }
            long endTime = System.currentTimeMillis();
            elapsedTime4NoLock.add(endTime - startTime);

            // synchronized 关键字（隐式锁）
            startTime = endTime;
            for (long i = 0; i < times; ) {
                i = addWithSynchronized(i);
            }
            endTime = System.currentTimeMillis();
            elapsedTime4Synchronized.add(endTime - startTime);

            // ReentrantLock 显式锁
            startTime = endTime;
            ReentrantLock lock = new ReentrantLock();
            for (long i = 0; i < times; ) {
                i = addWithReentrantLock(i, lock);
            }
            endTime = System.currentTimeMillis();
            elapsedTime4ReentrantLock.add(endTime - startTime);

            // cas
            startTime = endTime;
            AtomicLong atomicLong = new AtomicLong();
            while (atomicLong.getAndIncrement() < times) {
            }
            endTime = System.currentTimeMillis();
            elapsedTime4Cas.add(endTime - startTime);
        }

        System.out.println("无锁计算耗时: " + average(elapsedTime4NoLock) + "ms");
        System.out.println("synchronized计算耗时: " + average(elapsedTime4Synchronized) + "ms");
        System.out.println("ReentrantLock计算耗时: " + average(elapsedTime4ReentrantLock) + "ms");
        System.out.println("cas计算耗时: " + average(elapsedTime4Cas) + "ms");

    }

    /**
     * synchronized加锁
     */
    private synchronized long addWithSynchronized(long i) {
        i = i + 1;
        return i;
    }

    /**
     * ReentrantLock加锁
     */
    private long addWithReentrantLock(long i, Lock lock) {
        lock.lock();
        i = i + 1;
        lock.unlock();
        return i;
    }

    /**
     * 计算平均耗时
     */
    private double average(Collection<Long> collection) {
        return collection.stream().mapToLong(i -> i).average().orElse(0);
    }
}
