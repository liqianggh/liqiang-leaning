package java8date.format;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程安全问题
 *
 * @author Jann Lee
 * @date 2019-12-01 16:29
 **/
public class SimpleDateFormatTest  {
    private static final ThreadLocal<SimpleDateFormat> localSimpleDateFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd"));

    public static void main(String[] args) throws InterruptedException {
        int threadNum = 10;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        AtomicInteger atomicInteger = new AtomicInteger();
        // 创建线程池的正确姿势
        ThreadFactory threadFactory = r -> new Thread(null, r,"simple-date-format-" + String.format("%02d", atomicInteger.addAndGet(1)), 0);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(threadNum, threadNum, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),threadFactory, new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < threadNum; i++) {
            threadPoolExecutor.submit(() -> {
                try {
                    for (int j = 0; j < 100; j++) {
//                        System.out.println(Thread.currentThread().getName() + " : " + localSimpleDateFormat.get().parse("20191201"));
                        if (j % 2 == 0) {
                            Long time = date.getTime() + 1000;
                            date.setTime(time);
                        }
                        System.out.println(date);
                    }
//                } catch (ParseException e) {
//                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        System.out.println("执行完毕");
        threadPoolExecutor.shutdownNow();
    }
}
