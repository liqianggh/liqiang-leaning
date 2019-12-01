package ReadWriteLockTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁测试
 *
 * @author Jann Lee
 * @date 2019-11-28 21:48
 **/
public class ReadWriteLockTest {

    private static ExecutorService executor = Executors.newFixedThreadPool(100);

    public static void main(String[] args) {
        MyReadWriteLock readWriteLock = new MyReadWriteLock();
        MyLock readLock = readWriteLock.readLock();
        MyLock writeLock = readWriteLock.writeLock();
        String processName = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();

        String processID = processName.substring(0,processName.indexOf('@'));

        System.out.println("processID="+processID);
//        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
//        Lock readLock = readWriteLock.readLock();
//        Lock writeLock = readWriteLock.writeLock();

        for (int i = 0; i < 100; i++) {
            int finalI = i;
            executor.execute(() -> {
                try {
                    if (finalI %10 == 0) {
                        writeLock.lock();
                        System.out.println("[写锁]编号: " + finalI + "加写锁成功-----------------------------------------------------------------------------");
                        System.out.println("[写锁]执行中，其他任务必须在此等待3s");
                        TimeUnit.SECONDS.sleep(3);
                        System.out.println("[写锁]编号：" + finalI + "任务执行完毕");
                        writeLock.unlock();
                    } else {
                        Long start = System.currentTimeMillis();
                        readLock.lock();
                        TimeUnit.MILLISECONDS.sleep(100);
                        readLock.unlock();
                        System.out.println("[读锁]编号:" + finalI + "任务执行完毕， 用时：" + (System.currentTimeMillis() - start) + "ms");
                    }
                    readLock.unlock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}