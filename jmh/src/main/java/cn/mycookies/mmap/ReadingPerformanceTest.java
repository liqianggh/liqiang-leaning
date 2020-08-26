package cn.mycookies.mmap;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

import static cn.mycookies.mmap.RandomUtil.generateFile;
import static cn.mycookies.mmap.RandomUtil.randomPos;


/**
 * 以不同方式写文件 性能对比
 *
 * @author liqiang
 * @date 2020-08-23 9:54 上午
 **/
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 1, time = 3)
@Measurement(iterations = 3, time = 5)
@Fork(3)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class ReadingPerformanceTest {
    /**
     * 随机读取时，每次读取的长度
     */
    public static final int LEN = 4;
    /**
     * 10M，100M，200M
     */
    @Param(value = {"10", "100", "200"})
    private int fileSize;

    /**
     * 每个文件的读取次数 = fileSize * readTimeFactor
     */
    private final int ioTimesFactor = 10000;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ReadingPerformanceTest.class.getSimpleName())
                .result("files/benchmark-result/readPerformanceReport.json")
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }

    /**
     * 用FileOutputStream向文件写入内容
     */
    @Benchmark
    public void bioRead(Blackhole blackhole) throws IOException {
        File tempFile = generateFile("bio", fileSize);
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(tempFile, "r")) {
            byte[] temp = new byte[LEN];
            int readTimes = fileSize * ioTimesFactor;
            while (readTimes-- > 0) {
                // 移动指针位置到一个随机值
                randomAccessFile.seek(randomPos(fileSize));
                randomAccessFile.read(temp);
                // 消费临时数组，消除jit可能带来的优化
                blackhole.consume(temp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (!tempFile.delete()) {
                System.out.println("文件删除失败：" + tempFile.getName());
            }
        }
    }


    /**
     * 基于bio，读取文件
     */
    @Benchmark
    public void nioRead() throws IOException {
        File tempFile = generateFile("nio", fileSize);
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(tempFile, "r")) {
            FileChannel channel = randomAccessFile.getChannel();
            int readTimes = fileSize * ioTimesFactor;
            ByteBuffer byteBuffer = ByteBuffer.allocate(LEN);
            // 随机读取
            while (readTimes-- > 0) {
                channel.read(byteBuffer, randomPos(fileSize));
                byteBuffer.flip();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (!tempFile.delete()) {
                System.out.println("文件删除失败：" + tempFile.getName());
            }
        }
    }

    /**
     * mmap写文件
     */
    @Benchmark
    public void mMapRead(Blackhole blackhole) throws IOException {
        File tempFile = generateFile("mmap", fileSize);
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(tempFile, "r")) {
            MappedByteBuffer byteBuffer = randomAccessFile
                    .getChannel()
                    .map(FileChannel.MapMode.READ_ONLY, 0, tempFile.length());
            int readTimes = fileSize * ioTimesFactor;
            byte[] temp = new byte[LEN];
            while (readTimes-- > 0) {
                // 移动指针到任意位置
                byteBuffer.position(randomPos(fileSize));
                byteBuffer.get(temp);
                // 消费临时数组，消除jit可能带来的优化
                blackhole.consume(temp);
            }
        } finally {
            if (!tempFile.delete()) {
                System.out.println("文件删除失败：" + tempFile.getName());
            }
        }
    }
}