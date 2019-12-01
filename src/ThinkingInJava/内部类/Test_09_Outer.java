package cn.mycookies.ThinkingInJava.内部类;

/**
 * @author Jann Lee
 * @description TODO
 * @date 2019-03-11 23:14
 **/
public class Test_09_Outer {

    private void helloWolrd(){
        class MethodInner implements  Test_09_Interface{

            @Override
            public Test_09_Interface sayHello() {
                System.out.println("hello world!");
                return new MethodInner();
            }
        }
    }
}
