package cn.mycookies.concurrent.tools.CountDownLatchTest;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CountDownLatch使用案例
 *
 * @author cruer
 * @date 2019-12-24 1:09
 */
public class RunningRaceTest {
    public static void main(String[] args) throws InterruptedException {
        int size = 5;
        AtomicInteger counter = new AtomicInteger();
        // 使用线程池的正确姿势
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(size, size, 1000, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100), (r) -> new Thread(r, counter.addAndGet(1) + " 号 "),
                new ThreadPoolExecutor.AbortPolicy());
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < size; i++) {
            threadPoolExecutor.submit(new Runner(countDownLatch));
        }

        // 裁判等待5名选手准备完毕
        countDownLatch.await();
        System.out.println("裁判：比赛开始~~");

        threadPoolExecutor.shutdownNow();
    }


}

class Runner implements Runnable {
    private CountDownLatch countdownLatch;

    public Runner(CountDownLatch countdownLatch) {
        this.countdownLatch = countdownLatch;
    }

    @Override
    public void run() {
        try {
            int sleepMills = ThreadLocalRandom.current().nextInt(1000);
            Thread.sleep(sleepMills);
            System.out.println(Thread.currentThread().getName() + " 选手已就位, 准备共用时： " + sleepMills + "ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 准备完毕，举手示意
            countdownLatch.countDown();
        }
    }
}