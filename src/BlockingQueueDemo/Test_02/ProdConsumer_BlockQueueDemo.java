package BlockingQueueDemo.Test_02;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jann Lee
 * @description TODO
 * @date 2019-05-26 23:41
 **/
public class ProdConsumer_BlockQueueDemo {

    public static void main(String [] args) throws InterruptedException {
        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t 生产线程启动");
            try {
                myResource.myProd();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                myResource.stop();
            }
        }).start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+"\t 消费线程启动");
            try {
                myResource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                myResource.stop();
            }
        }).start();

        TimeUnit.SECONDS.sleep(30L);
        myResource.stop();

    }
}

class MyResource {
    /**
     * 默认开启，进行生产+消费
     */
    public volatile boolean FLAG = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    BlockingQueue<String> blockingQueue = null;

    public MyResource(BlockingQueue<String> blockingQueue) {
        if (Objects.nonNull(blockingQueue)) {
            this.blockingQueue = blockingQueue;
            System.out.println(blockingQueue.getClass().getName());
        }
    }

    public void myProd() throws Exception {
        String data = null;
        boolean returnValue;
        while (FLAG) {
            data = atomicInteger.incrementAndGet() + "";
            returnValue = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (returnValue) {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列" + data + "失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }
        System.out.println(Thread.currentThread().getName() + "\t 大老板叫停，表示flag==false了，生产结束");
    }

    public void myConsumer() throws Exception {
        String result = null;
        while (FLAG) {
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (Objects.isNull(result) || result.equals("")) {
                FLAG = false;
                System.out.println(Thread.currentThread().getName() + "\t 消费队列超过2s中没有消费成功，退出");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 消费队列" + result + "成功");
        }
        System.out.println(Thread.currentThread().getName() + "\t 大老板叫停，表示flag==false了，消费结束");
    }

    public void stop(){
        this.FLAG = false;
    }
}
