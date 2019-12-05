package concurrent.tools.semaphoreTest;

import java.util.concurrent.*;

/**
 * @description 信号量，时来控制同时访问特定资源的线程数量，它通过协调各个线程以保证合理使用公共资源
 *
 * 作用：控制并发执行数量a
 * @author Jann Lee
 * @date 2019-06-01 20:11
 **/
public class SemaphoreTest {
    private static final int THREAD_COUNG = 30;
    private static ExecutorService threadPool = new ThreadPoolExecutor(THREAD_COUNG,THREAD_COUNG,100, TimeUnit.SECONDS,new LinkedBlockingDeque<>(100));
    private static Semaphore semaphore = new Semaphore(10);
    public static void main(String[] args) {
        for(int i = 0; i < THREAD_COUNG ; i++ ){
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName()+ " save data" + semaphore.getQueueLength() );
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        threadPool.shutdown();
    }
}
