package cn.mycookies.ThinkingInJava.内部类;
/**
 * @author Jann Lee
 * @description TODO
 * @date 2019-03-11 22:41
 **/
public class Test_01_Outer {

    class Inner{
        private String name;

        public Inner(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Inner{" + "name='" + name + '\'' + '}';
        }
    }

    public Inner getInner(String name){
        return new Inner(name);
    }

    public static void main(String [] args){
        Test_01_Outer outer = new Test_01_Outer();

        Test_01_Outer.Inner inner = outer.getInner("hello");
        System.out.println(inner);
    }
}
