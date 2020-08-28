package cn.mycookies.nio;

import org.junit.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * 缓冲区，除了boolean的其他七种基本类型都有对应的缓冲区
 * <p>
 * 四个核心属性：
 * capacity: 容量，缓冲区的最大存储数据量；一旦声明，不能改变
 * limit:界限，表示缓冲区可以操作数据的大小;表示 limit后面的数据不能读写
 * position: 缓冲区正在操作数据的位置；
 * mark: 标记位置，可以通过reset 回到这个位置
 * 0 <= mark <= position <= limit <= capacity
 *
 * flip(): 读数据模式，position设置为0
 *
 * 直接缓冲区和非直接缓冲区：
 * 非直接缓冲区：通过allocate（）分配的缓冲区，缓冲区在堆中
 * 直接缓冲区：通过allocateDirect（），将缓冲区建立在堆外内存中
 *
 *
 *
 * @author liqiang
 * @date 2020-08-27 12:34 上午
 **/
public class BufferTest {

    @Test
    public void byteBufferTest() {
        // 申请一个大小为1024的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        printBuffer(byteBuffer);

        // put方法写数据
        byteBuffer.put("hello world".getBytes());
        printBuffer(byteBuffer);

        // flip切换读取(get)数据模式
        byte[] bytes = new byte[5];
        byteBuffer.flip();
        byteBuffer.get(bytes);
        System.out.println(new String(bytes));
        printBuffer(byteBuffer);

        // rewind 重复读数据
        byteBuffer.rewind();
        byteBuffer.get(bytes);
        System.out.println(new String(bytes));
        printBuffer(byteBuffer);


        // clear 清空缓冲区，只是移动position，不会删除数据，和ringBuffer类似
        byteBuffer.clear();
        byteBuffer.get(bytes);
        System.out.println(new String(bytes));
        printBuffer(byteBuffer);

        // mark 表示当前位置
        byteBuffer.mark();
        byteBuffer.get(bytes);
        System.out.println(new String(bytes));
        byteBuffer.reset();
        byteBuffer.get(bytes);
        System.out.println(new String(bytes));
    }

    private void printBuffer(Buffer buffer){
        System.out.println("-------------start------------");
        System.out.println("capacity: " + buffer.capacity());
        System.out.println("position: " + buffer.position());
        System.out.println("limit: " + buffer.limit());
        System.out.println("-------------end------------");
    }
}
