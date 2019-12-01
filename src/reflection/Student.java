package cn.mycookies.reflection;

/**
 * @author Jann Lee
 * @className Student
 * @description TODO
 * @date 2019-03-03 20:44
 **/
public class Student {

    public String name;
    private Integer age;
    private String gender;

    static{
        System.out.println("静态代码块...");
    }
    {
        System.out.println("非静态代码块...");
    }
    public Student() {
        System.out.println("构造方法...");
    }

    public Student(String name, Integer age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Student{" + "name='" + name + '\'' + ", age=" + age + ", gender='" + gender + '\'' + '}';
    }
}
