package cn.mycookies.ThinkingInJava.初始化与清理;

/**
 * @author Jann Lee
 * @className FinalizeDemo
 * @description TODO
 * @date 2019-03-06 23:25
 **/
public class FinalizeDemo {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize in invoked");
        super.finalize();
    }
}
