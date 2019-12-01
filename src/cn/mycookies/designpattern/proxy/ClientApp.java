package cn.mycookies.designpattern.proxy;

/**
 * @author Jann Lee
 * @className ClientApp
 * @description TODO
 * @date 2019-03-03 21:47
 **/
public class ClientApp {

    cn.mycookies.designpattern.proxy.Isubject subject;

    public ClientApp() {
        // 可能直接拿不到，或者一些性能原因不能这么做
//        this.isubject = new RealSubject();
        subject.process();
    }

    public void doTask(){
        subject.process();
    }
}
