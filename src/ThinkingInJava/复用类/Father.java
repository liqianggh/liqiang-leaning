package cn.mycookies.ThinkingInJava.复用类;

/**
 * @author Jann Lee
 * @className 复用类
 * @description TODO
 * @date 2019-03-09 22:41
 **/
public class Father {

    private String name;

    public Father() {
        System.out.println("Father no args ");
    }

    public Father(String name) {
        this.name = name;
        System.out.println("Father with args");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Father{" + "name='" + name + '\'' + '}';
    }
}

