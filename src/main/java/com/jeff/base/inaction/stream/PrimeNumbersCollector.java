package com.jeff.base.inaction.stream;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.IntStream;

/**
 * 质数收集器
 * @author jeff
 * <p>Date 2018/2/8</p>
 */
public class PrimeNumbersCollector
        implements Collector<Integer,
                             Map<Boolean, List<Integer>>,
                             Map<Boolean, List<Integer>>>{

    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<Boolean, List<Integer>>(){{
            put(true, new ArrayList<Integer>());
            put(false, new ArrayList<Integer>());
        }};
    }

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (Map<Boolean, List<Integer>> acc, Integer candidate) -> {
            acc.get(isPrime(acc.get(true), candidate)).add(candidate);
        };
    }

    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (map1, map2) -> {
            map1.get(true).addAll(map2.get(true));
            map1.get(false).addAll(map2.get(false));
            return map1;
        };
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
    }

    private static boolean isPrime(List<Integer> primes, int candidate){
        int candidateRoot = (int)Math.sqrt((double)candidate);
        return takeWhile(primes, i -> i<= candidateRoot)
                .stream()
                .noneMatch(p -> candidate % p == 0);
    }

    private static <A> List<A> takeWhile(List<A> list, Predicate<A> p){
        int i = 0;
        for (A item : list){
            if (!p.test(item)){
                return list.subList(0, i);
            }
            i++;
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println(IntStream.rangeClosed(2, 100).boxed().collect(new PrimeNumbersCollector()));
    }
}
