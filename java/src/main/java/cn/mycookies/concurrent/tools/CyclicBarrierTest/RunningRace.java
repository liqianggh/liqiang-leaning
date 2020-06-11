package cn.mycookies.concurrent.tools.CyclicBarrierTest;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jann Lee
 * @date 2019-12-11 0:28
 **/
public class RunningRace {
    public static void main(String[] args) {
        int size = 5;
        AtomicInteger counter = new AtomicInteger();
        // 使用线程池的正确姿势
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(size, size, 1000, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100), (r) -> new Thread(r, counter.addAndGet(1) + " 号 "),
                new ThreadPoolExecutor.AbortPolicy());

        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> System.out.println("比赛开始~~~"));
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.submit(new Runner(cyclicBarrier));
        }
    }


}

class Runner implements Runnable {
    private CyclicBarrier cyclicBarrier;

    public Runner(CyclicBarrier countdownLatch) {
        this.cyclicBarrier = countdownLatch;
    }

    @Override
    public void run() {
        try {
            int sleepMills = ThreadLocalRandom.current().nextInt(1000);
            Thread.sleep(sleepMills);
            System.out.println(Thread.currentThread().getName() + " 选手已就位, 准备共用时： " + sleepMills + "ms" + cyclicBarrier.getNumberWaiting());
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}