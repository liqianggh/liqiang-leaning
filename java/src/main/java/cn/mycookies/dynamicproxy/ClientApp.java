package cn.mycookies.dynamicproxy;


import java.lang.reflect.Proxy;

/**
 * @author Jann Lee
 * @date 2019-03-03 22:25
 **/
public class ClientApp {

    public static void main(String [] args){
       cn.mycookies.dynamicproxy.SubjectInvocationHandler inter = new cn.mycookies.dynamicproxy.SubjectInvocationHandler(new cn.mycookies.dynamicproxy.RealSubject());
        // 生成一个$Proxy0.class文件，这个文件即为动态生成的代理类文件
        String saveFileName = "$Proxy0.class";
        cn.mycookies.dynamicproxy.ProxyUtils.saveProxyClass(saveFileName, inter.getClass().getSimpleName(),new Class[]{cn.mycookies.dynamicproxy.ISubject.class});
        // 获取代理实例对象
        cn.mycookies.dynamicproxy.ISubject subject = (cn.mycookies.dynamicproxy.ISubject) Proxy.newProxyInstance(cn.mycookies.dynamicproxy.ISubject.class.getClassLoader(),new Class[]{cn.mycookies.dynamicproxy.ISubject.class},inter);
        subject.process();
    }
}
