package cn.mycookies.ThinkingInJava.内部类.test_06.pack2;

import cn.mycookies.ThinkingInJava.内部类.test_06.pack1.Test_06_Interface;

/**
 * @author Jann Lee
 * @description TODO
 * @date 2019-03-11 22:54
 **/
public class Test_06_Outer {

    protected class Inner implements Test_06_Interface {

        public Inner() {
        }

        @Override
        public String hello() {
            return "内部类";
        }
    }
}
