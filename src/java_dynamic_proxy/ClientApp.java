package java_dynamic_proxy;

import cn.mycookies.designpattern.proxy.Isubject;
import cn.mycookies.designpattern.proxy.RealSubject;

import java.lang.reflect.Proxy;

/**
 * @author Jann Lee
 * @className ClientApp
 * @description TODO
 * @date 2019-03-03 22:25
 **/
public class ClientApp {

    public static void main(String [] args){
       SubjectInvocationHandler inter = new SubjectInvocationHandler(new RealSubject());
        // 生成一个$Proxy0.class文件，这个文件即为动态生成的代理类文件
        String saveFileName = "$Proxy0.class";
        ProxyUtils.saveProxyClass(saveFileName, inter.getClass().getSimpleName(),new Class[]{Isubject.class});
        // 获取代理实例对象
        Isubject subject = (Isubject) Proxy.newProxyInstance(Isubject.class.getClassLoader(),new Class[]{Isubject.class},inter);
        subject.process();
    }
}
