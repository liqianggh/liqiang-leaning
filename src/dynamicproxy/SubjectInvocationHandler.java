package dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Jann Lee
 * @className SubjectInvocationHandler
 * @description 中介类与委托类构成了静态代理关系，
 * 在这个关系中，中介类是代理类，
 * 委托类就是委托类；
 * 代理类与中介类也构成一个静态代理关系，
 * 在这个关系中，中介类是委托类，代理类是代理类。
 * 也就是说，动态代理关系由两组静态代理关系组成，
 * 这就是动态代理的原理？？？？？
 * @date 2019-03-03 22:22
 **/
public class SubjectInvocationHandler implements InvocationHandler {

    private Object obj;

    public SubjectInvocationHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(java.lang.Object proxy, Method method, java.lang.Object[] args) throws Throwable {
        System.out.println("hello");
        Object result = method.invoke(obj,args);
        System.out.println("world");
        return result;
    }
}
