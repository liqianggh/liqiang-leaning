package cn.mycookies.jvm;

import java.io.*;

/**
 * 自定义类加载器测试
 *
 * @author Jann Lee
 * @date 2020-02-15 20:39
 **/
public class MyClassLoader extends ClassLoader {

    /**
     * 要加载类所在路径
     */
    private String directory;

    public MyClassLoader(String directory) {
        this.directory = directory;
    }

    @Override
    protected Class<?> findClass(String name) {
        try {
            String fileName = directory + File.separator + name;
            File file = new File(fileName);
            InputStream in = new FileInputStream(file);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            byte[] data = out.toByteArray();

            in.close();
            out.close();
            return super.defineClass(name, data, 0, data.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
