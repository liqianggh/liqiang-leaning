package java_dynamic_proxy;

import cn.mycookies.designpattern.proxy.Isubject;

/**
 * @author Jann Lee
 * @className RealSubject
 * @description TODO
 * @date 2019-03-03 22:18
 **/
public class RealSubject implements Isubject {
    @Override
    public void process() {
        System.out.println("Real subject doing something...");
    }
}
