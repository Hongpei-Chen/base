package com.jeff.base.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author jeffchen
 * <p>Date 2018-01-14 17:53</p>
 */
public class ToolUtil {

    public static String getRandom(int min, int max){
        Random random = new Random();
        int s = random.nextInt(max) % (max - min + 1) + min;
        return String.valueOf(s);
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 6; i++){
            String num = getRandom(1,16);
            if (!list.contains(num)){
                list.add(num);
            }
        }
        for (String n : list){
            System.out.println(n);
        }
    }
}
