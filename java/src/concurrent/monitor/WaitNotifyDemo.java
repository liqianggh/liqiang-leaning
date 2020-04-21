package concurrent.monitor;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 等待唤醒机制优化循环等待
 *
 * @author Jann Lee
 * @date 2019-12-12 22:21
 **/
public class WaitNotifyDemo {

    public static void main(String[] args) {
        waitNotifyTest();
    }

    public static void testWhileCondition() {
        // 是否还有包子
        AtomicBoolean hasBun = new AtomicBoolean();
        
        // 包子铺老板
        new Thread(() -> {
            try {
                // 一直循环查看是否还有包子
                while (true) {
                    if (hasBun.get()) {
                        Thread.sleep(4000);
                        System.out.println("老板：检查一下是否还剩下包子...");
                    } else {
                        System.out.println("老板：没有包子了, 马上开始制作...");
                        Thread.sleep(1000);
                        System.out.println("老板：包子出锅咯....");
                        hasBun.set(true);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {
            System.out.println("小强：我要买包子...");
            try {
                // 每隔一段时间询问是否完成
                while (!hasBun.get()) {
                    System.out.println("小强：包子咋还没做好呢~");
                    Thread.sleep(500);
                }
                System.out.println("小强：终于吃上包子了....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }




    public static void waitNotifyTest() {
        // 是否还有包子
        AtomicBoolean hasBun = new AtomicBoolean();
        // 锁对象
        Object lockObject = new Object();

        // 包子铺老板
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(1000);
                    synchronized (lockObject) {
                        while (hasBun.get()) {
                            System.out.println("老板：包子够卖了，打一把王者荣耀");
                            lockObject.wait();
                        }
                        System.out.println("老板：没有包子了, 马上开始制作...");
                        Thread.sleep(3000);
                        System.out.println("老板：包子出锅咯....");
                        hasBun.set(true);
                        // 通知等待的食客
                        lockObject.notifyAll();

                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {
            try {
                synchronized (lockObject) {
                    System.out.println("小强：我要买包子...");
                    while (!hasBun.get()) {
                        System.out.println("小强：看一下有没有做好， 看公众号cruder有没有新文章");
                        lockObject.wait();
                    }
                    System.out.println("小强：包子终于做好了，我要吃光它们....");
                    hasBun.set(false);
                    lockObject.notifyAll();
                    System.out.println("小强：一口气把店里包子吃光了， 快快乐乐去板砖了~~");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
