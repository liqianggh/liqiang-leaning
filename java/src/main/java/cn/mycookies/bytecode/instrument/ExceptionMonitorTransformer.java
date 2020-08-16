package cn.mycookies.bytecode.instrument;

import cn.mycookies.bytecode.asm.MyClassVisitor;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

/**
 * 类转换器
 *
 * @author Jann Lee
 * @date 2020-06-13 2:30 下午
 **/
public class ExceptionMonitorTransformer implements ClassFileTransformer {

    /**
     * class文件转换，如果没有转换返回null即可
     */
    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) {
        
        if (!className.contains("mycookies")) {
            return null;
        }
        try {
            ClassReader cr = new ClassReader(className);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            MyClassVisitor classVisitor = new MyClassVisitor(cw);
            cr.accept(classVisitor, ClassReader.EXPAND_FRAMES);
            byte[] data = cw.toByteArray();

            // 保存处理后的文件
            File f = new File("BaseHandler.class");
            String fileName = f.getAbsolutePath();
            FileOutputStream out = new FileOutputStream(f);
            out.write(data);
            out.close();
            System.out.println("修改后的字节码文件已生成，见 " + fileName);


            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
