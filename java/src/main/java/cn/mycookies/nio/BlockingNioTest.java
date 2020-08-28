package cn.mycookies.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 阻塞时NIO测试
 * <p>
 * 1. 通道：负责连接
 * java.nio.channels.Channel
 * |--SocketChannel
 * |--ServerSocketChannel
 * |--DatagramChannel
 * <p>
 * |--Pipe.SinkChannel
 * |--Pipe.SourceChannel
 * <p>
 * 2.缓冲区：负责数据的存取
 *
 * @author liqiang
 * @date 2020-08-29 12:20 上午
 **/
public class BlockingNioTest {

    @Test
    public void client() throws IOException {
        // 1. 建立网络通道 用于发送数据文件
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9999));
        // 2. 建立文件通道，读取本地文件
        FileChannel fileChannel = FileChannel.open(Paths.get("/Users/liqiang/code-repository/study/liqiang-leaning/files/README.md"), StandardOpenOption.READ, StandardOpenOption.WRITE);

        // 3. 建立缓冲区，用来存取文件
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (fileChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            channel.write(byteBuffer);
            byteBuffer.clear();
        }
        // 4. 关闭通道，释放资源
        channel.close();
        fileChannel.close();
    }


    @Test
    public void server() throws IOException {
        // 1. 建立网络通道 用于数据传输
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 2. 建立文件通道 用于写文件
        FileChannel fileChannel = FileChannel.open(Paths.get("2.txt"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        // 3. 绑定端口
        serverSocketChannel.bind(new InetSocketAddress(9999));
        // 4. 获取客户端连接的通道
        SocketChannel channel = serverSocketChannel.accept();

        // 5. 建立缓冲区 用于存取数据
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (channel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            fileChannel.write(byteBuffer);
            byteBuffer.clear();
        }

        // 6. 关闭通道，释放资源
        serverSocketChannel.close();
        fileChannel.close();
        channel.close();
    }

    @Test
    public void client2() throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9998));
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("Hello, NIO!".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        byteBuffer.clear();
        // 通知服务端 我写完了！
        socketChannel.shutdownOutput();

        int len;
        while ((len = socketChannel.read(byteBuffer)) != -1) {
            byteBuffer.flip();
            System.out.println("客户端收到了：" + new String(byteBuffer.array(), 0, len));
            byteBuffer.clear();
        }
        socketChannel.close();
    }

    @Test
    public void server2() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(9998));

        SocketChannel clientSocket = serverSocketChannel.accept();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int len;
        while ((len = clientSocket.read(byteBuffer)) != -1) {
            byteBuffer.flip();
            System.out.println("服务端收到了：" + new String(byteBuffer.array(), 0, len));
            byteBuffer.clear();
        }
        clientSocket.shutdownInput();

        // 通知客户端 我收到了
        byteBuffer.put("收到收到!".getBytes());
        byteBuffer.flip();
        clientSocket.write(byteBuffer);

        clientSocket.close();
    }
}
