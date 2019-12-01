package ConcurrentTools.CountDownLatchTest;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jann Lee
 * @description 通过countdownlatch控制执行顺序
 * @date 2019-06-01 17:09
 **/
public class CountDownLatchTest {
    public static void main(String[] args) throws Exception {
        CountDownLatch c = new CountDownLatch(3);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 4, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
        threadPoolExecutor.execute(() -> {
            System.out.println("hello: " + 1 +" count:" +c.getCount());
            c.countDown();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            c.countDown();
        });
        threadPoolExecutor.execute(() -> {
            System.out.println("hello: " + 2 +" count:" +c.getCount());
            c.countDown();
        });
        c.await();
        System.out.println("主线程执行.....");

        System.out.println(c.getCount());
    }
}
