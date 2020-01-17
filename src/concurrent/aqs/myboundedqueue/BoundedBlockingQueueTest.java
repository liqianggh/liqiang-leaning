package concurrent.aqs.myboundedqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jann Lee
 * @date 2019-12-11 22:34
 **/
public class BoundedBlockingQueueTest {
    public static void main(String[] args) {
        int size = 5;
        AtomicInteger counter = new AtomicInteger();
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(size, size, 1000, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), (r) -> new Thread(r, "线程-" + counter.addAndGet(1)), new ThreadPoolExecutor.AbortPolicy());

        BoundedBlockingQueue queue = new BoundedBlockingQueue(10);
        threadPool.execute(() -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    queue.add(i);
                    System.out.println("添加了: " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadPool.execute(() -> {
            for (int i = 0; i < 1000; i++) {
                try {
                    System.out.println("移除了： " + queue.remove());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
