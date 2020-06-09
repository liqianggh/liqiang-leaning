package cn.mycookies;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

/**
 * 测试StringBuilder的性能
 *
 * @author Jann Lee
 * @date 2020/6/9 1:06 下午
 **/
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 5)
@Threads(4)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class StringBuilderTest {
    @Param(value = {"10", "50", "100"})
    private int length;

    @Benchmark
    public void testStringAdd(Blackhole blackhole) {
        String a = "";
        for (int i = 0; i < length; i++) {
            a += i;
        }
        blackhole.consume(a);
    }

    @Benchmark
    public void testStringBuilderAdd(Blackhole blackhole) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(i);
        }
        blackhole.consume(sb.toString());
    }

    /**
     * Benchmark                               (length)  Mode  Cnt     Score     Error  Units
     * StringBuilderTest.testStringAdd               10  avgt    5   163.517 ±  15.430  ns/op
     * StringBuilderTest.testStringAdd               50  avgt    5  1916.981 ± 253.354  ns/op
     * StringBuilderTest.testStringAdd              100  avgt    5  6815.152 ± 917.946  ns/op
     * StringBuilderTest.testStringBuilderAdd        10  avgt    5    83.911 ±  16.999  ns/op
     * StringBuilderTest.testStringBuilderAdd        50  avgt    5   559.954 ±  58.858  ns/op
     * StringBuilderTest.testStringBuilderAdd       100  avgt    5  1205.039 ±  58.404  ns/op
     */
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(StringBuilderTest.class.getSimpleName())
                .result("result.json")
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }
}
