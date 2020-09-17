package cn.mycookies.cpucache;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 二维数组不同遍历方式性能对比
 *
 * @author liqiang
 * @date 2020-09-16 12:30 上午
 **/
public class CPUCacheBenchmarkTest extends BaseCPUCacheBenchmarkTest {

    @Benchmark
    public void readLongArrayByOrder(LongArrayState state, Blackhole blackhole) {
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                blackhole.consume(state.tempArr[i][j]);
            }
        }
    }


    @Benchmark
    public void readLongArrayBySkip(LongArrayState state, Blackhole blackhole) {
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                blackhole.consume(state.tempArr[j][i]);
            }
        }
    }


    @Benchmark
    public void readIntArrayByOrder(LongArrayState state, Blackhole blackhole) {
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
                blackhole.consume(state.tempArr[i][j]);
            }
        }
    }


    @Benchmark
    public void readIntArrayBySkip(LongArrayState state, Blackhole blackhole) {
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < arraySize; j++) {
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
