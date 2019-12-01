package ReadWriteLockTest;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Lock;

/**
 * 自定义读写锁
 *
 * @author liqiang
 * @time 2019/11/28 19:29
 */
public class MyReadWriteLock {
    Sync sync = new Sync();

    public ReadLock readLock(){
        return new ReadLock(sync);
    }

    public WriteLock writeLock(){
        return new WriteLock(sync);
    }

    public static class ReadLock  implements MyLock {
        private final Sync sync;

        public ReadLock(Sync sync) {
            this.sync = sync;
        }

        @Override
        public void lock() {
            sync.acquireShared(1);
        }

        @Override
        public void unlock() {
            sync.releaseShared(1);
        }
    }
    public static class WriteLock   implements MyLock{
        private final Sync sync;

        public WriteLock(Sync sync) {
            this.sync = sync;
        }

        @Override
        public void lock() {
            sync.acquire(1);
        }

        @Override
        public void unlock() {
            sync.release(1);
        }
    }
}

class Sync extends AbstractQueuedSynchronizer {

    /**
     * 获取同步状态
     */
    @Override
    protected boolean tryAcquire(int acquires) {
        Thread current = Thread.currentThread();
        int c = getState();
        // 如果状态不为0，说明有线程持有同步状态
         if (c != 0) {
            // 如果不是当前线程， 获取同步状态失败
            if (current != getExclusiveOwnerThread()) {
                return false;
            }
            // 是当前状态， 状态累加【可重入】
            compareAndSetState(c,c + acquires);
            return true;
        }
        // 尝试获取同步状态，失败
        if (!compareAndSetState(c, c + acquires)) {
            return false;
        } else {
            // 获取成功
            setExclusiveOwnerThread(current);
            return true;
        }
    }

    /**
     * 独占式-释放同步状态
     */
    @Override
    protected boolean tryRelease(int releases) {
        // 如果当前线程没有持有同步状态，但是尝试释放同步状态，抛出异常
        if (getExclusiveOwnerThread() != Thread.currentThread()) {
            throw new IllegalMonitorStateException();
        }
        int nextStatus = getState() - releases;
        boolean isFree = nextStatus == 0;
        if (isFree) {
            setExclusiveOwnerThread(null);
        }
        setState(nextStatus);
        return isFree;
    }

    @Override
    protected int tryAcquireShared(int acquires) {
        int c = getState();
        // 1. 如果同步状态被独占
        if (getExclusiveOwnerThread() != null) {
            return -1;
        }
        // 2. 修改同步状态
        boolean result = compareAndSetState(c, c + 1);
        if (result) {
            return 1;
        }
        return  -1;
    }

    @Override
    protected boolean tryReleaseShared(int releases) {
        if (getExclusiveOwnerThread() != null) {
            return false;
        }
        while (true) {
            int c = getState();
            int nextState = c - 1;
            return compareAndSetState(c, nextState);
        }
    }
}