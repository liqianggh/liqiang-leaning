package cn.mycookies.nio;

import org.junit.Test;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.SortedMap;

/**
 * NIO通道学习
 * 一，通道
 * Channel：用户源节点与目标节点的链接，在Java NIO中负责缓冲区中数据的传输。
 * Channel 本身不存储数据，因此需要缓冲区配合才能完成传输操作
 * <p>
 * 二，Channel的主要实现： FileChannel，
 * SocketChannel，
 * ServerSocketChannel，
 * DatagramChannel
 * <p>
 * 三，获取通道
 * 1. 支持通道的类提供getChannel()方法
 * > 磁盘IO：FileInputStream/RandomAccessFile
 * > 网络IO：Socket，ServerSocket，DatagramSocket
 * 2. JDK1.7中的NIO.2 各个通道提供了静态方法，open（）；Files工具类提供了方法，如newByteChannel()
 * <p>
 * 四，通道之间的数据传输
 * transferFrom()
 * transferTo
 * <p>
 * 五，分散与聚集
 * <p>
 * 六，字符集：Charset
 *
 * @author liqiang
 * @date 2020-08-27 1:08 上午
 **/
public class ChannelTest {

    @Test
    public void channelTest() {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inputStream = new FileInputStream("1.jpg");
            outputStream = new FileOutputStream("2.jpg");
            inChannel = inputStream.getChannel();
            outChannel = outputStream.getChannel();
            // 申请缓冲区（堆内存）
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (inChannel.read(byteBuffer) != -1) {
                // 切换读模式
                byteBuffer.flip();
                outChannel.write(byteBuffer);
                // 清空缓冲区
                byteBuffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(inChannel);
            close(outChannel);
            close(inputStream);
            close(outputStream);
        }

    }

    /**
     * mmap
     * 直接缓冲区
     */
    @Test
    public void testChannel2() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("a.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("b.jpg"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
        // 内存映射文件 -mmp 【只有bytebuffer支持】
        MappedByteBuffer inMappedBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        byte[] bytes = new byte[inMappedBuffer.limit()];
        inMappedBuffer.get(bytes);
        outMappedBuffer.put(bytes);

        close(inChannel);
        close(outChannel);
    }

    /**
     * 通道间数据传输[直接缓冲区]
     */
    @Test
    public void transferTest() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("a.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("b.jpg"), StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);
        /*
         * inChannel.transferTo(0, inChannel.size(), outChannel);
         */
        outChannel.transferFrom(inChannel, 0, inChannel.size());

        close(inChannel);
        close(outChannel);
    }

    @Test
    public void randomAccessFileTest() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("a.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();

        // 多个缓冲区:分散读取
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(2048);
        ByteBuffer[] byteBuffers = {byteBuffer, byteBuffer2};
        channel.read(byteBuffers);
        for (ByteBuffer buffer : byteBuffers) {
            buffer.flip();
            System.out.println(new String(buffer.array(), 0, buffer.limit()));
        }

        // 聚集写入
        RandomAccessFile randomAccessFile2 = new RandomAccessFile("b.txt", "rw");
        FileChannel channel2 = randomAccessFile2.getChannel();
        channel2.write(byteBuffers);

    }

    @Test
    public void charsetTest() throws CharacterCodingException {
        SortedMap<String, Charset> charsetSortedMap = Charset.availableCharsets();
        charsetSortedMap.forEach((key, value) -> System.out.println(key + "-" + value));

        Charset charset = Charset.forName("GBK");
        CharsetEncoder encoder = charset.newEncoder();
        CharsetDecoder decoder = charset.newDecoder();

        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("你好 nio");
        charBuffer.flip();

        ByteBuffer byteBuffer = encoder.encode(charBuffer);
        for (byte b : byteBuffer.array()) {
            System.out.println(b);
        }

        CharBuffer decode = decoder.decode(byteBuffer);
        decode.flip();
        for (char c : decode.array()) {
            System.out.println(c);
        }

    }


    /**
     * 关闭资源
     *
     * @param closeable 需要关闭的资源
     */
    private void close(Closeable closeable) {
        if (Objects.nonNull(closeable)) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
