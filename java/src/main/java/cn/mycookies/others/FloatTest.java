package main.java.cn.mycookies.others;

/**
 * float测试
 *
 * @author Jann Lee
 * @date 2019-12-25 0:17
 **/
public class FloatTest {
    public static void main(String[] args) {
        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.8f;

        if (a == b) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        Float m = Float.valueOf(a);
        Float n = Float.valueOf(b);
        if (m.equals(n)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        Double x = new Double(a);
        Double y = new Double(b);
        if (x.equals(y)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }
}
