package cn.mycookies.mmap;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.RandomUtils;

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
public class RandomUtil {

    public static final int BATCH_SIZE = 1024;

    public static char getRandomCharacter() {
        return (char) ('a' + Math.random() * ('z' - 'a' + 1));
    }

    /**
     * 创建一个文件
     *
     * @param prefix 文件前缀
     * @return 文件对象
     * @throws IOException 文件创建异常
     */
    public static File generateFileWithPrefix(String prefix) throws IOException {
        String fileName = prefix + UUID.randomUUID() + ".txt";
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
     * 获取文件的任意读取位置
     */
    public static int randomPos(int fileSize) {

        return RandomUtils.nextInt(0, fileSize * 1024 * 1024 - 4);
    }

    /**
     * 生成文件
     *
     * @param prefixName 文件名称前缀
     * @param size       文件大小，单位M
     */
    public static File generateFile(String prefixName, int size) throws IOException {
        File file = null;
        FileOutputStream outputStream = null;
        FileChannel channel = null;
        int fileSize = size;
        try {
            file = generateFileWithPrefix(prefixName);
            outputStream = new FileOutputStream(file);
            channel = outputStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(BATCH_SIZE);
            // 每次循环写入1M
            while (fileSize-- > 0) {
                for (int i = 0; i < BATCH_SIZE; i++) {
                    buffer.put(generate1KbRandomString().getBytes());
                    buffer.flip();     //此处必须要调用buffer的flip方法
                    channel.write(buffer);
                    buffer.clear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            if (channel != null) {
                channel.close();
            }
        }
        return file;
    }

}
