package concurrent.aqs.myboundedqueue;

import concurrent.aqs.mylock.MutexLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;

/**
 *  有界阻塞阻塞队列
 *
 * @author Jann Lee
 * @date 2019-12-11 22:20
 **/
public class BoundedBlockingQueue<T> {
    /**
     * list作为底层存储结构
     */
    private List<T> dataList;
    /**
     * 队列的大小
     */
    private int size;

    /**
     * 锁，和条件变量
     */
    private MutexLock lock;
    /**
     * 队列非空 条件变量
     */
    private Condition notEmpty;
    /**
     * 队列未满 条件变量
     */
    private Condition notFull;

    public BoundedBlockingQueue(int size) {
        dataList = new ArrayList<>();
        lock = new MutexLock();
        notEmpty = lock.newCondition();
        notFull = lock.newCondition();
        this.size = size;
    }


    /**
     * 队列中添加元素 [只有队列未满时才可以添加，否则需要等待队列变成未满状态]
     */
    public void add(T data) throws InterruptedException {
        lock.lock();
        try {
            // 如果队列已经满了， 需要在等待“队列未满”条件满足
            while (dataList.size() == size) {
                notFull.await();
            }
            dataList.add(data);
            Thread.sleep(2000);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 移除队列的第一个元素[只有队列非空才可以移除，否则需要等待变成队列非空状态]
     */
    public T remove() throws InterruptedException {
        lock.lock();
        try {
            // 如果为空， 需要在等待“队列非空”条件满足
            while (dataList.isEmpty()) {
                notEmpty.await();
            }
            T result = dataList.remove(0);
            notFull.signal();
            return result;
        } finally {
            lock.unlock();
        }
    }

}
