package cn.mycookies.cpucache;

import org.apache.commons.lang3.RandomUtils;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.concurrent.TimeUnit;

/**
 * CPU缓存验证
 *
 * @author liqiang
 * @date 2020/9/15 11:19 下午
 **/
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 1, time = 2)
@Measurement(iterations = 1, time = 10)
@Fork(1)
@State(value = Scope.Benchmark)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public abstract class BaseCPUCacheBenchmarkTest {
    /**
     * 二维数组大小 [arraySize][8]
     */
    @Param(value = {"256", "1024", "4096", "5120"})
    protected int arraySize;
    private static int maxArraySize;

    @Setup
    public void setup() {
        maxArraySize = arraySize;
    }

    @State(Scope.Thread)
    public static class LongArrayState {
        public long[][] tempArr;

        @Setup(Level.Invocation)
        public void arraySetup() {
            tempArr = generateLongArray(maxArraySize);
        }
    }


    @State(Scope.Thread)
    public static class IntArrayState {
        public int[][] tempArr;

        @Setup(Level.Invocation)
        public void arraySetup() {
            tempArr = generateIntArray(maxArraySize);
        }
    }

    public static long[][] generateLongArray(int x) {
        long[][] result = new long[x][maxArraySize];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < maxArraySize; j++) {
                result[i][j] = RandomUtils.nextInt(0, 10000);
            }
        }
        return result;
    }

    public static int[][] generateIntArray(int x) {
        int[][] result = new int[x][maxArraySize];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < maxArraySize; j++) {
                result[i][j] = RandomUtils.nextInt(0, 10000);
            }
        }
        return result;
    }
}
