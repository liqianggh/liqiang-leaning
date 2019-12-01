package BlockingQueueDemo.Test_01_HelloWorld;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @description TODO
 * @author Jann Lee
 * @date 2019-05-17 0:56
 **/
public class SynchronousQueueDemo {

    public static void main(String [] args){
        BlockingQueue<String> blockingQueue = new SynchronousQueue<>();

        new Thread(()->{
            try {
                System.out.println(Thread.currentThread().getName()+"\t put aaa");
                blockingQueue.put("aaa");
                System.out.println(Thread.currentThread().getName()+"\t put bbb");
                blockingQueue.put("bbb");
                System.out.println(Thread.currentThread().getName()+"\t put ccc");
                blockingQueue.put("ccc");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }," 线程1").start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println(Thread.currentThread().getName()+"\t  get "+blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"线程2").start();
    }
}
