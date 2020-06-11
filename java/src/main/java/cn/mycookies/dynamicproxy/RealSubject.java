package cn.mycookies.dynamicproxy;


/**
 * @author Jann Lee
 * @date 2019-03-03 22:18
 **/
public class RealSubject implements ISubject {
    @Override
    public void process() {
        System.out.println("Real subject doing something...");
    }
}
