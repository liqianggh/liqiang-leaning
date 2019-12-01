package cn.mycookies.ThinkingInJava.内部类;

/**
 * @author Jann Lee
 * @description TODO
 * @date 2019-03-11 23:02
 **/
public class Test_07_Outer {
    private String name;

    public Test_07_Outer(String name) {
        this.name = name;
    }

    private String sayName(){
        new Inner().gender = "213";
        return name;
    }

    class Inner{
        private String gender;
       public void changeNmae(String name){
            Test_07_Outer.this.name = name;
       }

       public void sayOuterHello(){
           System.out.println(sayName());
       }
    }

    public static void main(String [] args){
        Test_07_Outer outer = new Test_07_Outer("liqiang");

        Test_07_Outer.Inner inner = outer.new Inner();
        System.out.println(outer.sayName());
        inner.changeNmae("Jann");
        inner.sayOuterHello();
        System.out.println(outer.sayName());
    }
}
