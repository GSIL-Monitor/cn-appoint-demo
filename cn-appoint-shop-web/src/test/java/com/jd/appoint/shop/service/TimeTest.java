package com.jd.appoint.shop.service;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TimeTest {
    @Test
    public void getTime(){
        long timeStamp = 1531648859476L;
        Date date = new Date(timeStamp);
        System.out.println("================"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
    }

    @Test
    public void testStream(){
        List<Integer> integers = Arrays.asList(1,2,13,4,15,6,17,8,19);
        long count = integers.stream().count();
        System.out.println(count);
        Integer integer = integers.stream().findFirst().get();
        System.out.println(integer);
        String collect = integers.stream().map(i -> {
            return i + "join";
        }).collect(Collectors.joining(","));


        System.out.println(collect);
        List<String> collect1 = integers.stream().map(i -> {
            return i + "join";
        }).collect(Collectors.toList());


        System.out.println(collect1);
    }

}
