package com.jeff.base.inaction.stream;

import com.jeff.base.inaction.bean.Trader;
import com.jeff.base.inaction.bean.Transaction;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 使用流收集数据
 * @author jeff
 * <p>Date 2018/2/4</p>
 */
public class CollectionStreamDemo {

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
     * 按城市分组
     * @return
     */
    public static Map<String, List<Transaction>> groupByCity(){
        return transactions.stream()
                .collect(Collectors.groupingBy(transaction ->
                transaction.getTrader().getCity()));
    }

    /**
     * 按年份分组
     * @return
     */
    public static Map<Integer, List<Transaction>> groupByYear(){
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getYear));
    }

    /**
     * 先按城市分组再按年份分组
     * @return
     */
    public static Map<String, Map<Integer, List<Transaction>>> groupByCityThenYear(){
        return transactions.stream()
                .collect(Collectors.groupingBy(
                        trnas -> trnas.getTrader().getCity(),
                        Collectors.groupingBy(trans -> trans.getYear())));
    }

    /**
     * 获取最大交易值
     * @return
     */
    public static Optional<Transaction> maxValue(){
        return transactions.stream().collect(Collectors.maxBy(
                Comparator.comparingInt(Transaction::getValue)));
    }

    /**
     * 总交易价格
     * @return
     */
    public static int sumValue(){
        return transactions.stream().
                collect(Collectors.summingInt(Transaction::getValue));
    }

    public static void main(String[] args) {
       /* Optional<Transaction> transaction = maxValue();
        System.out.println(transaction.get().getValue());*/
       //年份分组
        Map<String, List<Transaction>> gByCity = groupByCity();
        System.out.println(gByCity);

        //先按城市分组再按年份分组
        Map<String, Map<Integer, List<Transaction>>> gByCityThenYear = groupByCityThenYear();
        System.out.println(gByCityThenYear);

    }


}
