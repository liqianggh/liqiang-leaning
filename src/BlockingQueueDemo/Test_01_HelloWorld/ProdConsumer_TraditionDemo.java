package BlockingQueueDemo.Test_01_HelloWorld;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1.线程 操作 资源类
 * 2.判断 干活 通知
 *
 * 题目：一个初始值为零的变量，两个线程交替执行，一个加一，一个减一，来五轮
 * @author Jann Lee
 * @description TODO
 * @date 2019-05-17 1:03
 **/
public class ProdConsumer_TraditionDemo {
    public static void main(String [] args){
        ShareData shareData = new ShareData();
        new Thread(() -> {
            for (int i = 0; i < 5; i ++) {
                shareData.incr();;
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i ++) {
                shareData.decr();;
            }
        }, "AA").start();
    }

}

class ShareData{
    public int number = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    public void incr(){
        lock.lock();
        try {
            // 1.判断
            while (number != 0) {
                condition.await();
            }
            // 2.干活
            number++;
            System.out.println(Thread.currentThread().getName()+"/t"+number);
            // 3.通知
            condition.signalAll();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decr(){
        lock.lock();
        try {
            // 1.判断
            while (number == 0) {
                condition.await();
            }
            // 2.干活
            number--;
            System.out.println(Thread.currentThread().getName()+"/t"+number);
            // 3.通知
            condition.signalAll();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
