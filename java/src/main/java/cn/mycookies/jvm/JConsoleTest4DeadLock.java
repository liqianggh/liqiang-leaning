package cn.mycookies.jvm;

/**
 * 用jconsole测试死锁
 *
 * @author Jann Lee
 * @date 2020-02-09 22:32
 **/
public class JConsoleTest4DeadLock implements Runnable{
    int a, b;

    public JConsoleTest4DeadLock(int a, int b) {
        this.a = a;
        this.b = b;
    }


    @Override
    public void run() {
        synchronized (Integer.valueOf(a)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (Integer.valueOf(b)) {
                System.out.println(a + b);
            }
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(new JConsoleTest4DeadLock(1, 2)).start();
            new Thread(new JConsoleTest4DeadLock(2, 1)).start();
        }
    }
}
