package com.jeff.base.inaction.lambda;

import com.jeff.base.inaction.bean.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 函数式接口使用demo
 * @author jeff
 * <p>Date 2018/1/20</p>
 */

public class LambdaDemo {


    /**
     *
     */
    public void userPredicate(){

    }

    /**
     * 使用局部变量
     */
    public void localVariable(){
        int param = 100;
        IntConsumer intc = (i) -> System.out.println(param);

    }

    /**
     * 方法引用
     */
    public void function(){
        //
        Function<String, Integer> stringToInt = Integer::parseInt;
        Integer i = stringToInt.apply("23");

//        BiPredicate<List<String>, String> ct = (list, s) -> list.contains(s);
        BiPredicate<List<String>, String> contains = List::contains;
        System.out.println(i);

    }

    /**
     * 构造器引用
     * Apple 排序，排序属性：weight
     */
    public void contructorCite(){
        //创建基本数据
        List<Integer> list = Arrays.asList(12, 23, 23, 43, 23, 34);
        List<Apple> apples = list.stream().map(Apple::new).collect(Collectors.toList());

        //排序
        //1. 行为参数写法
//        apples.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));

        //2. 方法引用
        apples.sort(Comparator.comparing(Apple::getWeight));


        //输出
        apples.forEach(apple -> System.out.println(apple.getWeight()));

    }

    public void orderApple(){

        //创建基本数据
        List<Integer> list = Arrays.asList(12, 23, 23, 43, 23, 34);
        List<Apple> apples = list.stream().map(Apple::new).collect(Collectors.toList());
        apples.forEach(apple -> {
           if (apple.getWeight() % 2 == 0){
               apple.setColor("green");
           }else {
               apple.setColor("red");
           }
        });
        //排序实现
        apples.sort(Comparator.comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));
        apples.forEach(apple -> System.out.println(String.format("Apple: %s, %d", apple.getColor(), apple.getWeight())));
    }

    public static void main(String[] args) {
        Supplier<LambdaDemo> supplier = LambdaDemo::new;
        supplier.get().orderApple();
    }


}
