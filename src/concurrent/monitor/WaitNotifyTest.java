package concurrent.monitor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liqiang
 * @time 2019/12/11 10:04
 */
public class WaitNotifyTest {
    public static void main(String[] args) {
        int threadNum = 4;
        AtomicInteger atomicInteger = new AtomicInteger();
        // 线程工厂： 给线程起个名字，方便定位问题
        ThreadFactory threadFactory = r -> new Thread(r, "线程-" + atomicInteger.addAndGet(1));
        // 线程池创建的正确姿势
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(threadNum, threadNum, 1000, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), threadFactory,  new ThreadPoolExecutor.AbortPolicy());

        // 数字打印对象
        NumberPrinter2 numberPrinter = new NumberPrinter2(100);
        // 启动5个线程并发打印，2个线程打印偶数，3个线程打印奇数

        threadPool.execute(numberPrinter::printEvenNumber);
        threadPool.execute(numberPrinter::printUnEvenNumber);
        threadPool.execute(numberPrinter::printEvenNumber);
        threadPool.execute(numberPrinter::printUnEvenNumber);


    }

}

class NumberPrinter {
    /**
     * 要打印数字的数量
     */
    private int number;

    private int maxNumber;

    public NumberPrinter(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public  void printEvenNumber() {
        while (number < maxNumber) {
            if (number / 2 ==0) {
                System.out.println(Thread.currentThread().getName() + "打印了" + number++);
                this.notify();
            } else {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized void printUnEvenNumber() {
        while (number < maxNumber) {
            if (number / 2 != 0) {
                System.out.println(Thread.currentThread().getName() + "打印了" + number++);
                this.notify();
            } else {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
class NumberPrinter2 {
    /**
     * 要打印数字的数量
     */
    private volatile int number;

    private int maxNumber;

    ReentrantLock lock = new ReentrantLock();
    Condition evenNumber = lock.newCondition();
    Condition unEvenNumber = lock.newCondition();

    public NumberPrinter2(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    public void printEvenNumber() {
        lock.lock();
        while (number < maxNumber) {
            if (number / 2 == 0) {
                System.out.println(Thread.currentThread().getName() + "打印了" + number++);
                unEvenNumber.signalAll();
            } else {
                try {
                    evenNumber.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        lock.unlock();
    }

    public void printUnEvenNumber() {
        lock.lock();
        while (number < maxNumber) {
            if (number / 2 != 0) {
                System.out.println(Thread.currentThread().getName() + "打印了" + number++);
                evenNumber.signalAll();
            } else {
                try {
                    unEvenNumber.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        lock.unlock();
    }
}
