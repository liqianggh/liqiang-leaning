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

import static cn.mycookies.mmap.RandomUtil.generateFileWithPrefix;
import static cn.mycookies.mmap.RandomUtil.getRandomCharacter;
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
public class WritingPerformanceTest {
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
                .include(WritingPerformanceTest.class.getSimpleName())
                .result("files/benchmark-result/writingPerformanceResult.json")
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }

    /**
     * 基于BIO，向文件写入内容
     */
    @Benchmark
    public void bioWrite() throws IOException {
        int writeTimes = fileSize * ioTimesFactor;
        File tempFile = generateFileWithPrefix("bio");
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(tempFile, "rw")) {
            while (writeTimes-- > 0) {
                randomAccessFile.seek(randomPos(fileSize));
                randomAccessFile.writeChar(getRandomCharacter());
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
     * 基于NIO，向文件写入内容
     */
    @Benchmark
    public void nioWrite() throws IOException {
        int writeTimes = fileSize * ioTimesFactor;
        File tempFile = generateFileWithPrefix("nio");
        try (FileChannel fileChannel = new RandomAccessFile(tempFile, "rw").getChannel()) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1);
            while (writeTimes-- > 0) {
                byteBuffer.put((byte) getRandomCharacter());
                byteBuffer.flip();
                fileChannel.write(byteBuffer, randomPos(fileSize));
                byteBuffer.clear();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (!tempFile.delete()) {
                System.out.println("文件删除失败：" + tempFile.getName());
            }
        }
    }

    @Benchmark
    public void mMapWrite() throws IOException {
        File tempFile = generateFileWithPrefix("mmap");
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(tempFile, "rw")) {
            int writeTimes = fileSize * ioTimesFactor;
            MappedByteBuffer byteBuffer = randomAccessFile
                    .getChannel()
                    .map(FileChannel.MapMode.READ_WRITE, 0, fileSize * 1024 * 1024);
            while (writeTimes-- > 0) {
                byteBuffer.putChar(randomPos(fileSize), getRandomCharacter());
            }
            byteBuffer.force();
        } finally {
            if (!tempFile.delete()) {
                System.out.println("文件删除失败：" + tempFile.getName());
            }
        }
    }

}