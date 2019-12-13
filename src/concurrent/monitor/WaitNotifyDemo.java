package concurrent.monitor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jann Lee
 * @date 2019-12-12 22:21
 **/
public class WaitNotifyDemo {

    public static void main(String[] args) {
        waitNotifyTest();
    }

    public static void testWhileConditon() {
        // 是否出餐成功
        AtomicInteger num = new AtomicInteger();
        new Thread(() -> {
            try {
                // 一直循环查看是否还有包子
                while (true) {
                    if (num.get() > 0) {
                        System.out.println("厨师：检查一下是否还剩下包子...");
                        Thread.sleep(1000);
                        continue;
                    }
                    System.out.println("厨师：没有包子了, 马上开始制作...");
                    Thread.sleep(1000);
                    System.out.println("厨师：包子出锅咯，一锅100个....");
                    num.set(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {
            System.out.println("强老板：我要买包子...");
            try {
                // 每隔一段时间询问是否完成
                while (num.get() <= 0) {
                    Thread.sleep(3000);
                    System.out.println("强老板：看一下有没有做好~");
                }
                System.out.println("强老板：终于吃上包子了....");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void waitNotifyTest() {
        AtomicInteger num = new AtomicInteger();
        Object object = new Object();
        // 厨师
        new Thread(() -> {
            try {
                while (true) {
                    synchronized (object) {
                        while (num.get() > 0) {
                            object.wait();
                        }
                        System.out.println("厨师：没有包子了, 马上开始制作...");
                        Thread.sleep(1000);
                        System.out.println("厨师：包子出锅咯，一锅100个....");
                        num.set(100);
                        // 通知所有等待的客户
                        object.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 强老板
        new Thread(() -> {
            System.out.println("强老板：点了100个包子...");
            try {
                synchronized (object) {
                    // 包子制作完成
                    while (num.get() <= 0) {
                        long startTime = System.currentTimeMillis();
                        System.out.println("强老板：在店里等待中，开始打王者荣耀了~");
                        object.wait();
                        long endTime = System.currentTimeMillis();
                        System.out.println("强老板：等待了" + (endTime - startTime) / 1000 + "秒后包子终于登场");
                    }
                    num.addAndGet(-100);
                    System.out.println("强老板：吃了100个包子");
                    object.notifyAll();
                    System.out.println("强老板：结帐走人~");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }
}
