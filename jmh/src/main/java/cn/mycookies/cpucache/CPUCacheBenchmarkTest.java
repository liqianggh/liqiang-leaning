package cn.mycookies.cpucache;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 二维数组遍历验证cpu缓存
 *
 * @author liqiang
 * @date 2020-09-16 12:30 上午
 **/
public class CPUCacheBenchmarkTest extends BaseCPUCacheBenchmarkTest {
    /**
     * 基于NIO，向文件写入内容
     */
    @Benchmark
    public void arrayRead1(IndexState state, Blackhole blackhole) {
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                // System.out.println("method-1 " + state.tempArr.hashCode() + " i=" + i + " j=" + j + " result = " +state.tempArr[i][j]);
                blackhole.consume(state.tempArr[i][j]);
            }
        }
    }

    /**
     * 基于NIO，向文件写入内容
     */
    @Benchmark
    public void arrayRead2(IndexState state, Blackhole blackhole) {
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                // System.out.println("method-2 " + state.tempArr.hashCode() + " i=" + j + " j=" + i + " result = " +state.tempArr[j][i]);
                blackhole.consume(state.tempArr[j][i]);
            }
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(CPUCacheBenchmarkTest.class.getSimpleName())
                .result("files/benchmark-result/cpuCacheTest.json")
                .resultFormat(ResultFormatType.JSON).build();
        new Runner(opt).run();
    }

}
