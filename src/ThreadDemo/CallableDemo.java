package ThreadDemo;

import javax.management.relation.RoleUnresolved;
import java.util.concurrent.Callable;

/**
 * 多线程创建的三种方式
 * 继承Thread类
 * 实现Runnable接口
 * 实现Callable接口
 * @author Jann Lee
 * @description TODO
 * @date 2019-05-27 0:04
 **/
class MyThread implements Runnable{
    @Override
    public void run() {

    }
}

class MyThread2 implements Callable {
    @Override
    public Object call() throws Exception {

        return null;
    }
}

public class CallableDemo {
}
