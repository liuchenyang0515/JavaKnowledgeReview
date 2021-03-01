package com.imooc.lambda;

/**
 * 四则运算接口
 * 自带的函数式接口只有一个参数Predicate<T>、Consumer<T,R>、Function<T>
 * 多个参数需要自定义函数式接口
 */
@FunctionalInterface//通知编译器这是函数式接口,进行抽象方法检查
public interface MathOperation {
    public Float operate(Integer a ,Integer b);
}
