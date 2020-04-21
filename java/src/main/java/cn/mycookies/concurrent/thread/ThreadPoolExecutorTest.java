package main.java.cn.mycookies.concurrent.thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 打开线程池的正确姿势
 *
 * @author Jann Lee
 * @date 2020-01-06 0:45
 **/
public class ThreadPoolExecutorTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 使用线程池的正确姿势
        AtomicInteger counter = new AtomicInteger();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3,
                3000, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5),
                (r) -> new Thread(r, counter.addAndGet(1) + " 号线程 "),
                new ThreadPoolExecutor.AbortPolicy());

        threadPoolExecutor.submit(new ThreadTaskTest());
        threadPoolExecutor.submit(new RunnableTaskTest());
        Future<String> future = threadPoolExecutor.submit(new CallableTaskTest());
        System.out.println(future.get());
        threadPoolExecutor.submit(() -> System.out.println(Thread.currentThread().getName() + "正在执行 hello  world"));

        threadPoolExecutor.shutdown();
    }
}
