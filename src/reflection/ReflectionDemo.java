package cn.mycookies.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @description Java反射相关代码测试
 * @author Jann Lee
 * @className ReflectionDemo
 * @date 2019-03-03 20:45
 **/
public class ReflectionDemo {


    public static void main(String[] args) throws Exception {
        reflectTestMethodsFields();
    }

    public static void reflectTestGetClassObj() throws ClassNotFoundException {
        // 获取class对象[不会执行静态代码块，代码块，和构造方法]
        Class<cn.mycookies.reflection.Student> clazzClass = cn.mycookies.reflection.Student.class;

        // 第二种：通过类名全路径获得[执行静态块但是不执行动态块儿（需要异常处理)]
        Class<cn.mycookies.reflection.Student> clazzForName = (Class<cn.mycookies.reflection.Student>) Class.forName("cn.mycookies.reflection.Student");

        // 第三种：通过实例对象获得[需要构造对象，执行静态代码块，代码块]
        cn.mycookies.reflection.Student student = new cn.mycookies.reflection.Student();
        Class<cn.mycookies.reflection.Student> clazzObj = (Class<cn.mycookies.reflection.Student>) student.getClass();
    }

    public static void reflectTestMethodsFields() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException, NoSuchMethodException {
        // 获取class对象
        Class<cn.mycookies.reflection.Student> clazz = (Class<cn.mycookies.reflection.Student>) Class.forName("cn.mycookies.reflection.Student");

        // 获取所有的构造方法
        Constructor<cn.mycookies.reflection.Student>[] constructiors = (Constructor<cn.mycookies.reflection.Student>[]) clazz.getConstructors();
        for(Constructor<cn.mycookies.reflection.Student> constructor: constructiors){
            System.out.println(constructor);
        }
        // 通过构方法 获取对象
        cn.mycookies.reflection.Student student = constructiors[0].newInstance();
        student.setAge(12);
        student.setGender("male");
        student.setName("Jann");
        System.out.println(student);
        cn.mycookies.reflection.Student student2 = constructiors[1].newInstance("Lucy",12,"female");
        System.out.println(student2);


        // 获取字段(getField只能获取到共有的，getDeclaredField则都可以获取到)
        Field name = clazz.getDeclaredField("name");
        cn.mycookies.reflection.Student student3 = clazz.newInstance();
        // 如果时私有属性，必须设置位true之后才可以设置
        name.setAccessible(true);
        name.set(student3,"hello");
        System.out.println(student3);


        // 获取方法
        Method setName = clazz.getMethod("setName",String.class);
        cn.mycookies.reflection.Student student4 = clazz.newInstance();
        setName.invoke(student4,"hello");
        System.out.println(student4);
        Method getName = clazz.getMethod("getName");
        String result = (String) getName.invoke(student4);
        System.out.println(result);
    }
}
