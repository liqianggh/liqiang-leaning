package cn.mycookies.concurrent.tools.exchangerTest;

import java.util.concurrent.Exchanger;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description 线程间交换数据的exchanger；
 * 他提供一个同步点，在这个同步点，两个线程可以交换彼此数据
 * @author Jann Lee
 * @date 2019-06-01 20:22
 **/
public class ExchangerTest {
    public static final Exchanger<String> exchanger = new Exchanger<>();
    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(2,2,300, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));

    public static void main(String[] args) {
        threadPool.execute(() -> {
            String aData = "hello aaa";
            try {
                exchanger.exchange(aData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        threadPool.execute(() -> {
            String bData = "world 123213";
            try {
               String aData =  exchanger.exchange(bData);
                System.out.println(aData);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

}
