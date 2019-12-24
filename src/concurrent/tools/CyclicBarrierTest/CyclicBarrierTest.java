package concurrent.tools.CyclicBarrierTest;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jann Lee
 * @description 让一组线程到达一个屏障（同步点）时被阻塞；当到达指定数量后才开始执行
 * @date 2019-06-01 17:33
 **/
public class CyclicBarrierTest {
    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> System.out.println("sdfdsfafasd"));

        int size = 5;
        AtomicInteger counter = new AtomicInteger();
        // 使用线程池的正确姿势
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(size, size, 1000, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100), (r) -> new Thread(r, counter.addAndGet(1) + " 号 "),
                new ThreadPoolExecutor.AbortPolicy());        for (int i = 0; i < 2; i++) {
            threadPoolExecutor.execute(() -> {
                System.out.println("hello:" + Thread.currentThread().getName());
                try {
                    cyclicBarrier.await();
                    System.out.println(123);
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        cyclicBarrier.await();

    }
}
