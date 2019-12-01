package BlockingQueueDemo.Test_01_HelloWorld;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Jann Lee
 * @date 2019-05-17 0:41
 **/
public class Test01 {

    public static void main(String[] args) throws InterruptedException {
        /**
         * 抛出异常组，当队列满时加入元素；当队列没有元素，移除元素；
         */
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue(3);
        blockingQueue.add("hello");
        blockingQueue.add("world");
        blockingQueue.add("blockingQueue");
        System.out.println(blockingQueue.element());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println("-------------------------------------");
        /**
         * 返回特殊值，当队列满，或队列空
         */
        System.out.println(blockingQueue.offer("111"));
        System.out.println(blockingQueue.offer("222"));
        System.out.println(blockingQueue.offer("333"));

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println("-------------------------------------");
        /**
         * 阻塞组
         */
        blockingQueue.put("hello");
        blockingQueue.put("hello2");
        blockingQueue.put("hello3");
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        /**
         * 超时组
         */
        System.out.println(blockingQueue.offer("111",2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("111",2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("111",2, TimeUnit.SECONDS));
        System.out.println(blockingQueue.offer("111",2, TimeUnit.SECONDS));

        System.out.println(blockingQueue.poll(2,TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2,TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2,TimeUnit.SECONDS));
        System.out.println(blockingQueue.poll(2,TimeUnit.SECONDS));

    }
}
