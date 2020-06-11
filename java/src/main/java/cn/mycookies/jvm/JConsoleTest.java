package cn.mycookies.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * -Xms100m -Xmx100m -XX:+UseSerialGC
 *
 * @author Jann Lee
 * @date 2020-02-06 0:29
 **/
public class JConsoleTest {

    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Thread.sleep(50);
            list.add(new OOMObject());
        }
        System.gc();
    }

    public static void createBusyThread() {
        Thread thread = new Thread(() -> {
            while (true) {
            }
        }, "test-busy-thread");
        thread.start();
    }

    public static void createLockThread(final Object lock) {
        Thread thread = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "test-lock-thread");
        thread.start();
    }

    public static void main(String[] args) throws InterruptedException {
        fillHeap(1000);
//        createBusyThread();
//        createLockThread(new Object());
    }

    static class OOMObject {
        public byte[] placeHolder = new byte[64 * 1024];
    }
}
