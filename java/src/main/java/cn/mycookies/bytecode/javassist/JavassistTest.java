package cn.mycookies.bytecode.javassist;

import cn.mycookies.bytecode.asm.BaseHandler;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * @author Jann Lee
 * @date 2020-06-11 6:58 下午
 **/
public class JavassistTest {

    public static void main(String[] args) throws Exception {
        CtClass ctClass = ClassPool.getDefault().get("cn.mycookies.bytecode.asm.BaseHandler");
        CtMethod ctMethod = ctClass.getDeclaredMethod("handle");
        ctMethod.getExceptionTypes();
        ctMethod.insertBefore("System.out.println(\"Start\");");
        ctMethod.insertAfter("System.out.println(\"222222top\");", true);
        Class<?> c = ctClass.toClass();
        ctClass.writeFile("./");
        BaseHandler handler = (BaseHandler) c.newInstance();
        handler.handle();
    }
}
