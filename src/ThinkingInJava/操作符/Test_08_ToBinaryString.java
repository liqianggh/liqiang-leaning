package cn.mycookies.ThinkingInJava.操作符;

/**
 * @author Jann Lee
 * @className TestToBinaryString
 * @description TODO
 * @date 2019-03-05 22:11
 **/
public class Test_08_ToBinaryString {

    // 展示十六进制和八进制计数法来操作long值，用Long.toBinaryString()来显示结果
    public static void main(String [] args){
        long num1 = 0x2c;
        System.out.println(num1);
        System.out.println(Long.toBinaryString(num1));
    }
}
