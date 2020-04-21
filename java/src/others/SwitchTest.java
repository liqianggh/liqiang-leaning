package others;

/**
 *
 * @author Jann Lee
 * @date 2019-12-25 0:12
 **/
public class SwitchTest {

    public static void main(String[] args) {
        // 当default在中间时输出是什么？
        int a = 1;
        switch (a) {
            case 2:
                System.out.println("print 2");
            case 1:
                System.out.println("print 1");
            default:
                System.out.println("first default print");
            case 3:
                System.out.println("print 3");
        }

        // 当switch括号内的变量为String类型的外部参数时，且看输出是什么？
        String param = null;
        switch (param){
            case "param":
                System.out.println("print param");
                break;
            case "String":
                System.out.println("print String");
                break;
            case "null":
                System.out.println("print null");
            default:
                System.out.println("second default print");
        }
    }

}
