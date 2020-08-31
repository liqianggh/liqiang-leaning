package cn.mycookies.mmap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.RandomUtils;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static cn.mycookies.mmap.BaseIOBenchmarkTest.OperationType.WRITE;
import static java.nio.channels.FileChannel.MapMode.READ_ONLY;
import static java.nio.channels.FileChannel.MapMode.READ_WRITE;

/**
 * IO性能测试的基类
 *
 * @author liqiang
 * @date 2020-08-31 2:30 下午
 **/
@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 1, time = 2)
@Measurement(iterations = 1, time = 10)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.SECONDS)
public abstract class BaseIOBenchmarkTest {
    /**
     * 10M，100M，200M
     */
    @Param(value = {"100", "200"})
    protected int fileSize;

    /**
     * 文件的最大字节数
     */
    private int maxIndexOfFile;
    /**
     * 本次测试的操作类型：读 / 写
     */
    protected OperationType operationType;

    /**
     * 支持随机访问的文件
     */
    protected RandomAccessFile randomAccessFile;
    /**
     * 临时文件
     */
    private File tempFile;

    /**
     * 文件通道，NIO时使用
     */
    protected FileChannel fileChannel;
    /**
     * 字节缓冲区，NIO测试时使用
     */
    protected ByteBuffer byteBuffer;

    /**
     * 映射字节缓冲区，Mmap测试使用
     */
    protected MappedByteBuffer mappedByteBuffer;

    /**
     * 操作类型，读 or 写
     */
    public abstract OperationType getOperationType();

    protected int getRandomPos() {
        return RandomUtils.nextInt(0, maxIndexOfFile);
    }

    /**
     * 资源初始化
     */
    @Setup
    public void setUp() throws IOException {
        long start = System.currentTimeMillis();

        maxIndexOfFile = fileSize * 1024 * 1024;
        operationType = getOperationType();
        // 根据读写类型，生成不同的文件，如果时读需要初始化文件的内容
        if (Objects.equals(operationType, WRITE)) {
            tempFile = FileGeneratorUtil.generateFileWithPrefix();
        } else {
            tempFile = FileGeneratorUtil.generateFile(fileSize);
        }
        randomAccessFile = new RandomAccessFile(tempFile, operationType.getPermissions());
        byteBuffer = ByteBuffer.allocate(1);
        fileChannel = randomAccessFile.getChannel();
        FileChannel.MapMode mapMode = Objects.equals(operationType, OperationType.READ) ? READ_ONLY : READ_WRITE;
        mappedByteBuffer = fileChannel.map(mapMode, 0, maxIndexOfFile);

        System.out.println("setUp 用时：" + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * 资源释放
     */
    @TearDown
    public void release() throws IOException {
        if (Objects.nonNull(randomAccessFile)) {
            randomAccessFile.close();
        }
        if (Objects.nonNull(fileChannel)) {
            fileChannel.close();
        }
        if (Objects.nonNull(tempFile) && !tempFile.delete()) {
            System.out.println("删除测试文件失败");
        }
    }

    /**
     * 操作类型
     */
    @Getter
    @AllArgsConstructor
    @ToString
    public enum OperationType {
        /**
         * 读，写两种操作
         */
        READ("r"), WRITE("rw");
        /**
         * 当前操作所需要的权限
         */
        private final String permissions;
    }
}
