package com.imooc.stream;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream流对象的五种创建方式
 * 第1、2种方式很常用
 * 第3、4、5种方式了解
 */
public class StreamGenerator {
    //1.基于数组进行创建
    @Test
    public void generator1() {
        String[] arr = {"Lily", "Andy", "Jackson", "Smith"};
        Stream<String> stream = Stream.of(arr);
        stream.forEach(s -> System.out.println(s));
    }

    //2.基于集合进行创建
    @Test
    public void generator2() {
        List<String> list = new ArrayList<>();
        list.add("Lily");
        list.add("Andy");
        list.add("Jackson");
        list.add("Smith");
        Stream<String> stream = list.stream();
        stream.forEach(s -> System.out.println(s));
    }

    //3.利用generate方法创建无限长度流
    @Test
    public void generator3() {
        Stream<Integer> stream = Stream.generate(() -> new Random().nextInt(100000));
        stream.limit(10).forEach(i -> System.out.println(i)); // 产生10次，不指定就会无限打印
    }

    //4.基于迭代器创建流
    @Test
    public void generator4() {
        Stream<Integer> stream = Stream.iterate(1, n -> n + 1); // 起始数据和步长
        stream.limit(100).forEach(i -> System.out.println(i));
    }

    //5.基于字符序列创建流，只有对每个字符进行处理的场景才会考虑用这种情况，比如数据加密
    @Test
    public void generator5() {
        String str = "abcdefg我的";
        IntStream stream = str.chars();
        stream.forEach(c -> System.out.println((char) c));// Unicode码加上(char)转换就是现实原字符，比如中文，不加(char)就显示Unicode编号
    }
}
