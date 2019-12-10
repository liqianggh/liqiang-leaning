package concurrent.tools.CountDownLatchTest;

import java.util.concurrent.*;

/**
 * @author Jann Lee
 * @date 2019-12-11 0:09
 **/
public class RunningRace {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            threadPoolExecutor.submit(new Runner(countDownLatch));
        }
    }


}

class Runner implements Runnable {
    private CountDownLatch countdownLatch;

    public Runner(CountDownLatch countdownLatch) {
        this.countdownLatch = countdownLatch;
    }

    @Override
    public void run() {
        // 1. 准备阶段
        try {
            int sleepMills = ThreadLocalRandom.current().nextInt(1000);
            Thread.sleep(sleepMills);
            System.out.println(Thread.currentThread().getName() + " 选手已就位, 准备共用时： " + sleepMills + "ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            countdownLatch.countDown();
        }

        // 2. 就绪阶段，等待其他选手就绪
        try {
            countdownLatch.await();
            System.out.println(Thread.currentThread().getName() + " 选手弹射起步~~~ ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}