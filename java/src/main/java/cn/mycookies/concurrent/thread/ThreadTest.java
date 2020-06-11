package cn.mycookies.concurrent.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程的三种方式
 *
 * @author Jann Lee
 * @date 2020-01-05 12:53
 **/
public class ThreadTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Thread thread1 = new ThreadTaskTest();
        Thread thread2 = new Thread(new ThreadTaskTest());
        FutureTask<String> futureTask = new FutureTask<>(new CallableTaskTest());
        Thread thread3 = new Thread(futureTask);

        thread1.start();
        thread2.start();
        thread3.start();
        long start = System.currentTimeMillis();
        System.out.println(futureTask.get());
        System.out.println("执行CallableTaskTest共用时：" + (System.currentTimeMillis()-start) + "ms");
    }
}

/**
 * 第一种
 */
class ThreadTaskTest extends Thread {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "正在执行 继承Thread类，重写run方法");
    }
}
/**
 * 第二种
 */
class RunnableTaskTest implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "正在执行 实现Runnable接口的任务");
    }
}

/**
 * 第三种
 */
class CallableTaskTest implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "正在执行 实现Callable接口的任务");
        return "hello callable!";
    }
}
