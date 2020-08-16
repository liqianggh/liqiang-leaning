package cn.mycookies.bytecode.instrument;

import java.lang.instrument.Instrumentation;

/**
 * 加载代理类，用来动态替换jvm中的class
 *
 *  @author Jann Lee
 * @date 2020/6/15 10:12 上午
 **/
public class AgentApplication {

    public static void premain(String args, Instrumentation inst) {
        System.out.println("premain1 --------- : " + args);
        //指定我们自己定义的Transformer，在其中利用Javassist做字节码替换
        inst.addTransformer(new ExceptionMonitorTransformer(), true);
        Class[] classList = inst.getAllLoadedClasses();
        try {
//            重定义类并载入新的字节码【针对已经加载的类】
//            inst.retransformClasses(BaseHandler.class);
            System.out.println("Agent Load Done.");
        } catch (Exception e) {
            System.out.println("Agent Load Failed!");
        }
    }

    public static void agentmain(String[] args) {
        System.out.println("Attaching ..........");
    }

}