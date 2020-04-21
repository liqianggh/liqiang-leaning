package main.java.cn.mycookies.jvm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 测试VisualVM
 *
 * @author Jann Lee
 * @date 2020-02-09 22:51
 **/
public class VisualVMTest4BTrace {
    public int add(int a, int b) {
        return a+b;
    }

    public static void main(String[] args) throws IOException {
        VisualVMTest4BTrace visualVMTest4BTrace = new VisualVMTest4BTrace();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        for(int i = 0; i < 20 ; i++ ){
            reader.readLine();
            int a = (int) Math.round(Math.random() * 1000);
            int b = (int) Math.round(Math.random() * 1000);
            System.out.println(visualVMTest4BTrace.add(a, b));
        }
    }

}
