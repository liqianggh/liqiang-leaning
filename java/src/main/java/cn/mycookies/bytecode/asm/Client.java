package cn.mycookies.bytecode.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;


/**
 * 测试
 *
 * @author Jann Lee
 * @date 2020-06-12 10:27 上午
 **/
public class Client {

    public static void main(String[] args) throws Exception {
        // 读取class文件
        ClassReader classReader = new ClassReader("cn.mycookies.bytecode.asm.BaseHandler");
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        // 处理
        MyClassVisitor classVisitor = new MyClassVisitor( classWriter);
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);
        byte[] data = classWriter.toByteArray();

        // 保存处理后的文件
        File f = new File("BaseHandler.class");
        String fileName = f.getAbsolutePath();
        FileOutputStream out = new FileOutputStream(f);
        out.write(data);
        out.close();
        System.out.println("修改后的字节码文件已生成，见 " + fileName);

        BaseHandler baseHandler = new BaseHandler();
        baseHandler.handle();
    }

}
