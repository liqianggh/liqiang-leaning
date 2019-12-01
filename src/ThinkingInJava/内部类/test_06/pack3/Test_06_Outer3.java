package cn.mycookies.ThinkingInJava.内部类.test_06.pack3;

import cn.mycookies.ThinkingInJava.内部类.test_06.pack1.Test_06_Interface;
import cn.mycookies.ThinkingInJava.内部类.test_06.pack2.Test_06_Outer;

/**
 * @author Jann Lee
 * @description TODO
 * @date 2019-03-11 22:57
 **/
public class Test_06_Outer3 extends Test_06_Outer {

    public Test_06_Interface getInner(){
        return new Inner();
    }
}
