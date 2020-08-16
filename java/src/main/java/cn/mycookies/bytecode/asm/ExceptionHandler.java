package cn.mycookies.bytecode.asm;

import aj.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * 异常处理器
 *
 * @author Jann Lee
 * @date 2020-06-17 10:22 上午
 **/
public class ExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    /**
     * 生成item的cache，判断是否有冲突
     */
    static Set<String> UNIQUE_ITEM_NAME_CACHE = new HashSet<>();

    /**
     * 调用{@link ExceptionHandler#handle(Throwable, String, String)}方法时用到的一些字节码
     * 可以使用 https://plugins.jetbrains.com/plugin/5918-asm-bytecode-outline 插件查看方法调用的字节码
     */
    private static final String EXCEPTION_HANDLE_CLASS = ExceptionHandler.class.getCanonicalName().replace(File.separator, ".");
    private static final String EXCEPTION_HANDLE_METHOD = "handle";
    private static final String EXCEPTION_HANDLE_PARAM = "(Ljava/lang/Throwable;Ljava/lang/String;Ljava/lang/String;)V";

    /**
     * 注入处理逻辑
     * 调用异常处理方法 {@link ExceptionHandler#handle(Throwable, String, String)}
     *
     * @param visitor    方法visitor
     * @param className  类名
     * @param methodName 方法名
     */
    public static void injectHandleLogic(MethodVisitor visitor, String className, String methodName) {
        visitor.visitInsn(Opcodes.DUP);
        visitor.visitLdcInsn(className);
        visitor.visitLdcInsn(methodName);
        visitor.visitMethodInsn(Opcodes.INVOKESTATIC, EXCEPTION_HANDLE_CLASS, EXCEPTION_HANDLE_METHOD, EXCEPTION_HANDLE_PARAM, false);
    }

    /**
     * 处理异常的逻辑
     * 方法签名请勿修改 {@link ExceptionHandler#injectHandleLogic(MethodVisitor, String, String)}中调用
     *
     * @param e          要处理的异常
     * @param className  类名
     * @param methodName 方法名
     * @param <T>        异常范型
     */
    public static <T extends Throwable> void handle(T e, String className, String methodName) {
        String itemName = generateItemName(e.getClass().getSimpleName(), className, methodName);
//        ZMonitorProxy.sum(itemName);
        System.out.println(itemName + "=================");
    }

    public static <T extends Throwable> void passException(T e, String methodName, int count, String tag) {
        System.out.println(methodName + " 发生了异常， 异常所属tag： " + tag + "， 累加值：" + count);
    }


    /**
     * 生成一个唯一的itemName（ZMonitor中用到，全局唯一的异常标识）
     * 唯一性保证， 类名+方法名+异常名，如果冲突则在后面追加序号如  ExceptionHandler.handle.Exception.2
     * 序号最大会重试1000此，如果还不行，则使用UUID
     *
     * @param exceptionClassName 异常类名
     * @param className          类名
     * @param methodName         方法名
     * @return 字符串
     */
    private static String generateItemName(String exceptionClassName, String className, String methodName) {
        className = className.replace(File.separator, ".");
        String clusterName = "zzpaycore";
        String originItemName = clusterName + "." + className + "." + methodName + "." + exceptionClassName;

        // 如果itemName已经被占用，则在后面增加序号，从2开始
        if (!UNIQUE_ITEM_NAME_CACHE.add(originItemName)) {
            int i = 2;
            int maxLoopTime = 1000;
            while (!UNIQUE_ITEM_NAME_CACHE.add(originItemName + i) && i < maxLoopTime) {
                i++;
            }
            if (i >= maxLoopTime) {
                i = UUID.randomUUID().clockSequence();
            }
            String itemName = originItemName + "." + i;
            logger.info("desc=ExceptionHandler.handle itemName冲突，重试后的结果为：{}", itemName);
            return itemName;
        }
        return originItemName;
    }

    /**
     * 释放本地缓存，程序运行时不需要这些数据
     */
    public static void releaseCache() {
        UNIQUE_ITEM_NAME_CACHE.clear();
    }
}
