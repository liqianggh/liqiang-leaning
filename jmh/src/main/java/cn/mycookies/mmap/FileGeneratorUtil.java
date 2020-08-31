package cn.mycookies.mmap;

import com.google.common.base.Preconditions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.UUID;

/**
 * 随机工具类，用来生成随机数据
 *
 * @author liqiang
 * @date 2020-08-25 5:39 下午
 **/
public class FileGeneratorUtil {

    public static File generateFileWithPrefix() throws IOException {
        String fileName = UUID.randomUUID() + ".txt";
        File file = new File("/Users/liqiang/code-repository/study/tempData", fileName);
        if (!file.exists()) {
            Preconditions.checkArgument(file.createNewFile(), "创建文件失败");
            System.out.println("生成文件：" + file.getAbsolutePath());
        }
        return file;
    }


    /**
     * 生成1024字符 = 1024 Byte = 1kb
     */
    public static String generate1KbRandomString() {
        // 36个字符
        StringBuilder uuid = new StringBuilder(UUID.randomUUID().toString());
        return uuid.append(uuid).append(uuid).append(uuid).append(uuid).append(uuid.subSequence(0, 447)) + "\r";
    }


    /**
     * 生成文件
     *
     * @param prefixName 文件名称前缀
     * @param size       文件大小，单位M
     */
    public static File generateFile(int size) throws IOException {
        File file = generateFileWithPrefix();
        int fileSize = size;
        try (FileChannel channel = new FileOutputStream(file).getChannel();) {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 每次循环写入1M
            while (fileSize-- > 0) {
                for (int i = 0; i < 1024; i++) {
                    buffer.put(generate1KbRandomString().getBytes());
                    buffer.flip();
                    channel.write(buffer);
                    buffer.clear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("生成文件失败， 文件名：" + file.getName());
        }
        return file;
    }

}
