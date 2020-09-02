package cn.mycookies.mmap;

import java.io.IOException;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * IO操作基准测试，对比传统IO和Mmap
 *
 * @author liqiang
 * @date 2020-08-31 6:06 下午
 **/
public class ReadingBenchmark extends BaseIOBenchmarkTest {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ReadingBenchmark.class.getSimpleName())
                .result("files/benchmark-result/readingBenchmark.json")
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }

    @Benchmark
    public void bioRead(Blackhole blackhole) throws IOException {
        // 移动指针位置到一个随机值
        randomAccessFile.seek(getRandomPos());
        byte result = randomAccessFile.readByte();
        blackhole.consume(result);
    }

    @Benchmark
    public void nioRead(Blackhole blackhole) throws IOException {
        fileChannel.read(byteBuffer, getRandomPos());
        byteBuffer.flip();
        blackhole.consume(byteBuffer);
        byteBuffer.clear();
    }

    @Benchmark
    public void mMapRead(Blackhole blackhole) {
        mappedByteBuffer.position(getRandomPos());
        byte result = mappedByteBuffer.get();
        blackhole.consume(result);
    }

    @Override
    public OperationType getOperationType() {
        return OperationType.READ;
    }
}
