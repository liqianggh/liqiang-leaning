package java8date.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程安全问题
 *
 * @author Jann Lee
 * @date 2019-12-01 16:29
 **/
public class DateTimeFormatterTest {

    public static void main(String[] args) throws InterruptedException {
        int threadNum = 10;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        CountDownLatch countDownLatch = new CountDownLatch(10);
        AtomicInteger atomicInteger = new AtomicInteger();
        // 创建线程池的正确姿势
        ThreadFactory threadFactory = r -> new Thread(null, r,"simple-date-format-" + String.format("%02d", atomicInteger.addAndGet(1)), 0);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(threadNum, threadNum, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),threadFactory, new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < threadNum; i++) {
            threadPoolExecutor.submit(() -> {
                try {
                    for (int j = 0; j < 100; j++) {
                        System.out.println(Thread.currentThread().getName() + " : " + LocalDate.parse("20191201", dateTimeFormatter).toString());
                    }
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
