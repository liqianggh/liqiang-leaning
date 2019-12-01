package cn.mycookies.ThinkingInJava.内部类;

/**
 * @author Jann Lee
 * @description TODO
 * @date 2019-03-11 22:44
 **/
public class Test_03_Outer {

    private java.lang.String gender;

    public Test_03_Outer(java.lang.String gender) {
        this.gender = gender;
    }

    class Inner{
        @Override
        public java.lang.String toString() {
            return java.lang.String.valueOf(Test_03_Outer.this.gender);
        }
    }

    public static void main(String [] args){
        Test_03_Outer.Inner str = new Test_03_Outer("hello0").new Inner();

        System.out.println(str);
    }
}
