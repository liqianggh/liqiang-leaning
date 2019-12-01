package cn.mycookies.designpattern.proxy;

/**
 * @author Jann Lee
 * @description 代理类可以手动创建，亦可以使用工具生成，如Java中的动态代理
 * @date 2019-03-03 21:48
 **/
public class SubjectProxy implements Isubject {
    @Override
    public void process() {
        // 对RealSubject的一层间接访问
        // do something
    }
}
