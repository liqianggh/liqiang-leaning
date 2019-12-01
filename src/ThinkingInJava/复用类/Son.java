package cn.mycookies.ThinkingInJava.复用类;

/**
 * @author Jann Lee
 * @className Son
 * @description TODO
 * @date 2019-03-09 22:42
 **/
public class Son extends Father {

    private String name;

    private String age;

    public Son() {
    }


    public Son(String name, String name1, String age) {
        super(name);
        this.name = name1;
        this.age = age;
    }
}
