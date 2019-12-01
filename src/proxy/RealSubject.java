package cn.mycookies.designpattern.proxy;

/**
 * @author Jann Lee
 * @className RealSubject
 * @description TODO
 * @date 2019-03-03 21:45
 **/
public class RealSubject implements Isubject{

    @Override
    public void process() {
        System.out.println("RealSubject do something...");
    }
}
