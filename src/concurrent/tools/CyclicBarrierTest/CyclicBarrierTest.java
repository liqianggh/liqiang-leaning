package concurrent.tools.CyclicBarrierTest;

import java.util.concurrent.*;

/**
 * @author Jann Lee
 * @description 让一组线程到达一个屏障（同步点）时被阻塞；当到达指定数量后才开始执行
 * @date 2019-06-01 17:33
 **/
public class CyclicBarrierTest {
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {

        int cpus = Runtime.getRuntime().availableProcessors();

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(11, 100, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10));
        for (int i = 0; i < 1; i++) {
            threadPool.execute(() -> {
                System.out.println("hello:" + Thread.currentThread().getName());
                try {
                    System.out.println("getNumberWaiting  : "+ cyclicBarrier.getNumberWaiting());
                    cyclicBarrier.await();
                    System.out.println("getNumberWaiting  : "+ cyclicBarrier.getNumberWaiting());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        cyclicBarrier.await();
        System.out.println("主线程执行了222");
        threadPool.shutdown();

    }
}
