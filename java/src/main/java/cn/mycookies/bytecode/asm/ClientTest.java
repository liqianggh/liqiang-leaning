package cn.mycookies.bytecode.asm;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 测试
 *
 * @author Jann Lee
 * @date 2020-06-12 10:27 上午
 **/
public class ClientTest {

    /**
     * jvm参数 : -noverify
     * -javaagent:/Users/liqiang/code-repository/work/AgentApplication/out/artifacts/AgentApplication/AgentApplication.jar
     */
    @Test
    public void modifyClass() throws InterruptedException {
        BaseHandler baseHandler = new BaseHandler();
        baseHandler.handle();
    }

    @Test
    public void generateClass() throws IOException {

        // 读取class文件
        Class<BaseHandler> target = BaseHandler.class;
        ClassReader classReader = new ClassReader(target.getResourceAsStream("BaseHandler.class"));
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);
        // 处理
        MyClassVisitor classVisitor = new MyClassVisitor(classWriter);
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);

        // 保存处理后的文件
        File f = new File("BaseHandler.class");
        String fileName = f.getAbsolutePath();
        FileOutputStream out = new FileOutputStream(f);
        out.write(classWriter.toByteArray());
        out.close();
        System.out.println("修改后的字节码文件已生成，见 " + fileName);
    }

    public static void main(String[] args) throws InterruptedException {
        BaseHandler baseHandler = new BaseHandler();
        baseHandler.handle();
    }

}
