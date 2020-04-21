package main.java.cn.mycookies.concurrent.deadlock;

/**
 * 死锁案例
 * 死锁产生条件：
 * 1. 互斥，资源只能被一个线程锁占有
 * 2. 占有且等待
 * 3. 不可抢占
 * 4. 循环（相互）等待
 *
 * @author liqiang
 * @time 2020/1/16 17:12
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        Account accountA = new Account("账户A", 1000);
        Account accountB = new Account("账户B", 1000);

        Thread th1 = new Thread(() -> accountA.transform(accountB, 100), "转账线程-1");
        Thread th2 = new Thread(() -> accountB.transform(accountA, 200), "转账线程-2");

        th1.start();
        th2.start();
    }
}

/**
 * 银行账户
 */
class Account {
    /**
     * 账户名
     */
    private String name;
    /**
     * 账户余额
     */
    private int balance;

    public Account(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }

    /**
     * 转账给账户{@code account} 共{@code money} 元
     *
     * @param account 指定账户
     * @param count   钱数
     * @return 转账成功返回true，否则false
     */
    public boolean transform(Account account, int count) {
        if (count > balance) {
            return false;
        }
        // 先锁当前账户
        synchronized (this) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 再锁对方账户
            synchronized (account) {
                account.balance += count;
                this.balance += count;
                System.out.println(this.name + " 成功转账给 " + account.name + " " + count + "元");
            }
        }
        return true;
    }
}
