package synchronizedtest;

/**
 * Synchronized的三种用法
 *
 * @author Jann Lee
 * @date 2019-11-04 22:02
 **/
public class SynchronizedTest {
    /**
     * 锁对象
     */
    private Object LOCK_OBJECT = new Object();

    /**
     * synchronized修饰静态方法，锁是当前类
     */
    public static synchronized void test1() {
        // do something
    }

    /**
     * synchronized修饰普通方法，锁是调用改方法的对象
     */
    public synchronized void test2() {
        // do something
    }

    /**
     * synchronized修饰代码块，锁是括号中的对象(obj)
     */
    public void test3() {
        synchronized (LOCK_OBJECT) {
            // do something
        }
    }

}
