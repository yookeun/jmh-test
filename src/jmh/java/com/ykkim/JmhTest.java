package com.ykkim;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 1,  jvmArgs = {"-Xms4G", "-Xmx4G"})
public class JmhTest {
    private static final long N = 10_000_000L;

    @Benchmark
    public long oldSum() {
        long result = 0;
        for (long i = 0; i <= N; i++) {
            result += i;
        }
        return result;
    }

    @Benchmark
    public long squentialSum() {
        return Stream.iterate(1L, i -> i + 1)
                .limit(N)
                .reduce(0L, Long::sum);
    }

    @Benchmark
    public long parallelSum() {
        return Stream.iterate(1L, i -> i + 1)
                .limit(N)
                .parallel()
                .reduce(0L, Long::sum);
    }

    @Benchmark
    public long rangedSum() {
        return LongStream.rangeClosed(1, N).reduce(0L, Long::sum);
    }

    @Benchmark
    public long rangedParallelSum() {
        return LongStream.rangeClosed(1, N).parallel().reduce(0L, Long::sum);
    }

}
