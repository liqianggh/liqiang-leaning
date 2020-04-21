package main.java.cn.mycookies.concurrent.tools.CountDownLatchTest;

/**
 * @description 一个线程或多个线程等待其他线程完成
 * @author Jann Lee
 * @date 2019-06-01 17:03
 **/
public class OrderByJoinMethod {

    public static void main(String [] args) throws InterruptedException {
        testByJoin();
    }

    public static void testByJoin() throws InterruptedException {

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" is running");
            }
        }, "Thread 1 ");

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" is running");
            }
        }, "Thread 2" +
                "");

        thread1.start();
        thread2.start();
        thread2.join();
        thread1.join();
        System.out.println("all thread is ran");
    }


}
