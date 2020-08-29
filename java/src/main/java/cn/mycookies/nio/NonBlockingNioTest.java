package cn.mycookies.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 非阻塞式IO测试【网络】
 *
 * @author liqiang
 * @date 2020-08-29 10:43 下午
 **/
public class NonBlockingNioTest {

    public static void main(String[] args) throws IOException {
        NonBlockingNioTest nonBlockingNioTest = new NonBlockingNioTest();
        nonBlockingNioTest.client();
    }

    @Test
    public void client() throws IOException {
        SocketChannel clientSocketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 6666));
        clientSocketChannel.configureBlocking(false);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            byteBuffer.put(scanner.next().getBytes());
            byteBuffer.flip();
            clientSocketChannel.write(byteBuffer);
            byteBuffer.clear();
        }

        clientSocketChannel.close();
    }

    @Test
    public void server() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(6666));
        serverSocketChannel.configureBlocking(false);

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 1. 监听事件
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 2. 轮询监听选择器上已准备就绪的事件
        while (selector.select() > 0) {
            // 获取所有选择器中注册的 已就绪的监听事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                // 根据不同的事件 做不同的操作
                if (selectionKey.isAcceptable()) {
                    // a. 客户端切换成非阻塞模式
                    SocketChannel channel = serverSocketChannel.accept();
                    // b.切换为非阻塞模式
                    channel.configureBlocking(false);
                    // c. 将该通道注册到选择器上
                    channel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    // 如果是读事件就绪，则读取数据
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    int len;
                    while ((len = channel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        System.out.println("服务端收到了：" + new String(byteBuffer.array(), 0, len));
                        byteBuffer.clear();
                    }
                }
                // 事件处理完之后 取消注册
                iterator.remove();
            }
        }
    }



}
