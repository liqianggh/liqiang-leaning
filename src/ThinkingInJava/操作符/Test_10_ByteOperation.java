package cn.mycookies.ThinkingInJava.操作符;

/**
 * @author Jann Lee
 * @className Test_10_ByteOperation
 * @description TODO
 * @date 2019-03-05 22:27
 **/
public class Test_10_ByteOperation {
    // 编写一个具有两个常量的程序，一个是10交替，两一个是01交替，最低为分别是0和1，让他们进行按位操作，输出结果。
    public static void main(String [] args){
        int num1 = 0xaaa;
        int num2 = 0x555;

        System.out.println(Integer.toBinaryString(num1)+"\n" +Integer.toBinaryString(num2));
        System.out.println(Integer.toBinaryString(num1 & num2));
        System.out.println(Integer.toBinaryString(num1 | num2));
        System.out.println(Integer.toBinaryString(num1 ^ num2));
        System.out.println("--------------------------------");

        System.out.println(Integer.toBinaryString(~num1 )+"\n"+ Integer.toBinaryString(~num2));

        System.out.println("--------------------------------");
        System.out.println(Integer.toBinaryString(num1 & num1));
        System.out.println(Integer.toBinaryString(num1 | num1));
        System.out.println(Integer.toBinaryString(num1 ^ num1));
        System.out.println(Integer.toBinaryString(~ num1));

        System.out.println("--------------------------------");

        System.out.println(Integer.toBinaryString(-1));
        System.out.println(Integer.toBinaryString(3));
//        00000000000000000000000000000001原码
//        11111111111111111111111111111110    反码
//        11111111111111111111111111111111  补码=反码+1
    }


}
