package com.imooc.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamMethod {
    //提取集合中所有偶数并求和
    @Test
    public void case1() {
        List<String> list = Arrays.asList("1", "2", "3", "4", "5", "6");
        int sum = list.stream() //获取stream对象
                .mapToInt(s -> Integer.parseInt(s)) //mapToInt将流中每一个数据转为整数
                .filter(n -> (n & 1) == 0) //filter对流数据进行过滤
                .sum();//求和, 这个sum只在intStream有，如果刚刚是map而不是mapToInt，那么最后是Stream，无法调用sum
        System.out.println(sum);
    }

    //所有名字首字母大写
    @Test
    public void case2() {
        List<String> list = Arrays.asList("lily", "smith", "jackson");
        List newList = list.stream()
                //按规则对每一个流数据进行转换
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1))
                //.forEach(s-> System.out.println(s));
                //collect对流数据进行收集,生成新的List/Set
                .collect(Collectors.toList()); // 这里支持toSet()，可以去重
        System.out.println(newList);
    }

    //将所有奇数从大到小进行排序,且不许出现重复
    @Test
    public void case3() {
        List<Integer> list = Arrays.asList(1, 60, 38, 21, 51, 60, 51, 73);
        List newList = list.stream().distinct()// 去除重复的流数据
                .filter(n -> (n & 1) == 1)
                .sorted((a, b) -> b - a) // 流数据排序，可以看接口声明理解原理
                .collect(Collectors.toList());
        System.out.println(newList);
    }
}
