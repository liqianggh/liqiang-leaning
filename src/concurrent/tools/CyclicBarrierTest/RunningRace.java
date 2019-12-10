package concurrent.tools.CyclicBarrierTest;

import java.util.concurrent.*;

/**
 * @author Jann Lee
 * @date 2019-12-11 0:28
 **/
public class RunningRace {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> System.out.println("比赛开始"));
        for (int i = 0; i < 5; i++) {
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
        // 1. 准备阶段
        try {
            int sleepMills = ThreadLocalRandom.current().nextInt(1000);
            Thread.sleep(sleepMills);
            System.out.println(Thread.currentThread().getName() + " 选手已就位, 准备共用时： " + sleepMills + "ms" + cyclicBarrier.getNumberWaiting());
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        // 2. 就绪阶段，等待其他选手就绪
        System.out.println(Thread.currentThread().getName() + " 选手弹射起步~~~ ");

    }
}