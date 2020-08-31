package cn.mycookies.mmap;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;

/**
 * Mmap，与传统IO 的 写性能基准测试
 *
 * @author liqiang
 * @date 2020-08-31 7:18 下午
 **/
public class WritingBenchmark extends BaseIOBenchmarkTest {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(WritingBenchmark.class.getSimpleName())
                .result("files/benchmark-result/writingBenchmark.json")
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }


    /**
     * 基于BIO，向文件写入内容
     */
    @Benchmark
    public void bioWrite() throws IOException {
        int randomPos = getRandomPos();
        randomAccessFile.seek(randomPos);
        randomAccessFile.writeChar((byte) 53);
    }

    /**
     * 基于NIO，向文件写入内容
     */
    @Benchmark
    public void nioWrite() throws IOException {
        int randomPos = getRandomPos();
        fileChannel.position(randomPos);
        byteBuffer.put((byte) 53);
        byteBuffer.flip();
        fileChannel.write(byteBuffer);
        byteBuffer.clear();
    }

    @Benchmark
    public void mMapWrite() {
        mappedByteBuffer.put(getRandomPos(), (byte) 53);
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.WRITE;
    }
}
