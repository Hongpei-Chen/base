package com.jeff.base.inaction.stream;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author jeff
 * <p>Date 2018/2/9</p>
 */
public class ParallelStream {

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .reduce(0L, Long::sum);
    }

    public static long iterativeSum(long n){
        long result = 0;
        for (long i = 1L; i <= n; i++){
            result += i;
        }
        return result;
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    public long measureSumPerf(Function<Long, Long> adder, long n){
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++){
            long start = System.nanoTime();
            long sum = adder.apply(n);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + sum);
            if (duration < fastest){
                fastest = duration;
            }
        }
        return fastest;
    }

    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .reduce(0L, Long::sum);
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    //测试各种方法计算总和的效率
    public static void measureSum(){
        ParallelStream ps = new ParallelStream();
        //Parallel sum done in:300 msecs
        System.out.println("Parallel sum done in:" +
                ps.measureSumPerf(ParallelStream::parallelSum, 10_000_000) + " msecs");
        //Sequential sum done in: 122 msecs
        System.out.println("Sequential sum done in: " +
                ps.measureSumPerf(ParallelStream::sequentialSum, 10_000_000) + " msecs");
        //Iterative sum done in:4 msecs
        System.out.println("Iterative sum done in:" +
                ps.measureSumPerf(ParallelStream::iterativeSum, 10_000_000) + " msecs");
        //RangedSum sum done in:5 msecs
        System.out.println("RangedSum sum done in:" +
                ps.measureSumPerf(ParallelStream::rangedSum, 10_000_000) + " msecs");
        //ParallelRangedSum sum done in:2 msecs
        System.out.println("ParallelRangedSum sum done in:" +
                ps.measureSumPerf(ParallelStream::parallelRangedSum, 10_000_000) + " msecs");
    }

    public static long sideEffectSum(long n){
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).forEach(accumulator::add);
        return accumulator.total;
    }

    /**
     * 该方法会出现并行执行问题，
     * forEach中调用的方法有副作用，它会改变多个线程共享的对象的可变状态
     * 所以避免共享可变状态
     * @param n
     * @return
     */
    public static long sideEffectParallelSum(long n){
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }

    public static void main(String[] args) {
        ParallelStream ps = new ParallelStream();
//        measureSum();
        System.out.println("SideEffect parallel sum done in: " +
                ps.measureSumPerf(ParallelStream::sideEffectSum, 10_000_000L));
        System.out.println("SideEffectParallelSum parallel sum done in: " +
                ps.measureSumPerf(ParallelStream::sideEffectParallelSum, 10_000_000L));
    }
}


class Accumulator{

    public long total = 0;
    public void add(long value){
        total += value;
    }
}
