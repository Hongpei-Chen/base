package com.jeff.base.inaction.stream;

import com.jeff.base.inaction.bean.Trader;
import com.jeff.base.inaction.bean.Transaction;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author jeff
 * <p>Date 2018/1/21</p>
 */
public class StreamDemo {

    private static List<Transaction> transactions;

    static {
         transactions = Arrays.asList(
                 new Transaction(new Trader("Brian","Cambridge"), 2011, 300),
                 new Transaction(new Trader("Raoul", "Cambridge"), 2012, 1000),
                 new Transaction(new Trader("Raoul", "Cambridge"), 2011, 400),
                 new Transaction(new Trader("Mario","Milan"), 2012, 710),
                 new Transaction(new Trader("Mario","Milan"), 2012, 700),
                 new Transaction( new Trader("Alan","Cambridge"), 2012, 950)
         );
    }

    /**
     * 找出2011年的所有交易并按交易额排序（从低到高）
     */
    public static List<Transaction> sortLowToHightByYearAndValue(){
        return transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
    }

    /**
     * 例子：获取单一属性列表
     * 交易员都在哪些不同的城市工作过
     * @return 城市工作列表
     */
    public static List<String> workCities(){
        return transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * 例子：用于字符串拼接
     * 返回所有交易员的姓名字符串，按字母顺序排序
     */
    public static String getTraderNames(){
        return transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.joining("/"));
    }

    public static Integer highestValue(){
        /*Optional<Integer> option = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
                return option.get();*/
        //或
        Optional<Transaction> option = transactions.stream()
                .max(Comparator.comparing(Transaction::getValue));
        return option.get().getValue();
    }

    //原始流
    public static void countTransction(){
        int totalVal = transactions.parallelStream()
                .mapToInt(Transaction::getValue)
                .sum();
        OptionalInt max = transactions.parallelStream()
                .mapToInt(Transaction::getValue)
                .max();

        System.out.println("总交易额：" + totalVal);
        System.out.println("最大交易额：" + max.getAsInt());
    }

    /**
     * 获取100以内的勾股数
     */
    public static void pythagoreanTriples(){
        Stream<int[]> stream =
                IntStream.rangeClosed(1, 100).boxed()
                        .flatMap(a -> IntStream.rangeClosed(a, 100)
                                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                                .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                        );
        stream.forEach(t -> System.out.println(t[0] + "," + t[1] + "," + t[2]));
    }

    //文件流
    public static void readFile(){
        try(Stream<String> lines = Files.lines(Paths.get("F:/InteillJWork/base/src/main/resources/busniessProtocol.html"),
                Charset.defaultCharset())) {
            lines.forEach(System.out::println);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //无限流
    public static void infiniteStream(){

    }


    public static void main(String[] args) {
        /*System.out.println("2011年的所有交易并按交易额从低到高排序: " + sortLowToHightByYearAndValue().toString());
        System.out.println("交易员工作城市：" + workCities().toString());
        System.out.println("所有交易员的姓名字符串，按字母顺序排序: " + getTraderNames());
        countTransction();*/
//        pythagoreanTriples();

        readFile();
    }
}
