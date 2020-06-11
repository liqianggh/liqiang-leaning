package cn.mycookies.dynamicproxy;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 代理工具类，用于保存生成的代理类
 *
 * @author Jann Lee
 * @date 2019-03-03 22:40
 **/
public class ProxyUtils {

    /**
     * Save proxy class to path
     *
     * @param path           path to save proxy class
     * @param proxyClassName name of proxy class
     * @param interfaces     interfaces of proxy class
     * @return
     */
    public static boolean saveProxyClass(String path, String proxyClassName, Class[] interfaces) {
        if (proxyClassName == null || path == null) {
            return false;
        }

        // get byte of proxy class
        byte[] classFile = ProxyGenerator.generateProxyClass(proxyClassName, interfaces);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path);
            out.write(classFile);
            out.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}