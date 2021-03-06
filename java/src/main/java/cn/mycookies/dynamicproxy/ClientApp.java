package cn.mycookies.dynamicproxy;


import java.lang.reflect.Proxy;

/**
 * @author Jann Lee
 * @date 2019-03-03 22:25
 **/
public class ClientApp {

    public static void main(String [] args){
        SubjectInvocationHandler inter = new SubjectInvocationHandler(new RealSubject());
        // 生成一个$Proxy0.class文件，这个文件即为动态生成的代理类文件
        String saveFileName = "$Proxy0.class";
        ProxyUtils.saveProxyClass(saveFileName, inter.getClass().getSimpleName(),new Class[]{ISubject.class});
        // 获取代理实例对象
        ISubject subject = (ISubject) Proxy.newProxyInstance(ISubject.class.getClassLoader(),new Class[]{ISubject.class},inter);
        subject.process();
    }
}
