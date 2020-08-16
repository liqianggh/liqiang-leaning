package cn.mycookies.bytecode.asm;


import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

/**
 * 字节码访问
 * https://tech.meituan.com/2019/09/05/java-bytecode-enhancement.html
 *
 * @author Jann Lee
 * @date 2020-06-11 5:47 下午
 **/
public class MyClassVisitor extends ClassVisitor implements Opcodes {

    private static final String SKIP_METHOD = "<init>";

    static String className;
    public MyClassVisitor(int api) {
        super(api);
    }

    public MyClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    public MyClassVisitor(ClassWriter classWriter) {
        super(ASM5, classWriter);
    }

    @Override
    public void visit(int version, int access, String name, String signature,
                      String superName, String[] interfaces) {

        className = name;
        cv.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        // 类中有两个方法：无参构造以及process方法，这里不增强构造方法
        if (!name.equals(SKIP_METHOD) && mv != null) {
            mv = new MyMethodVisitor(mv, name);
        }
        return mv;
    }

    static class MyMethodVisitor extends MethodVisitor implements Opcodes {
        private String methodName;
        public MyMethodVisitor(MethodVisitor mv, String methodName) {
            super(Opcodes.ASM5, mv);
            this.methodName = methodName;
        }

        private List<Label> exceptionHandlers = new ArrayList<>();


        @Override
        public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
            exceptionHandlers.add(handler);
            super.visitTryCatchBlock(start, end, handler, type);
        }

        /**
         * 访问标签时判断，如果时catch中的异常异常代码块，则进行增强
         */
        @Override
        public void visitLabel(Label label) {
            super.visitLabel(label);
            if(exceptionHandlers.contains(label)) {
                super.visitInsn(Opcodes.DUP);
                super.visitLdcInsn(methodName);
                super.visitInsn(ICONST_1);
                super.visitLdcInsn(" middle ");
                super.visitMethodInsn(INVOKESTATIC, "cn/mycookies/bytecode/asm/ExceptionHandler", "passException", "(Ljava/lang/Exception;Ljava/lang/String;ILjava/lang/String;)V", false);
            }
        }
    }

}
