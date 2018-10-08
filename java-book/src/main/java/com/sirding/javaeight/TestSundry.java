package com.sirding.javaeight;

import org.junit.Test;

import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * jdk8 杂项测试
 *
 * @author zc.ding
 * @create 2018/10/8
 */
public class TestSundry {
    
    @Test
    public void test1(){
        System.out.println(String.join(",", "a", "b", "c"));
    }
    
    @Test
    public void test2(){
        String content = "abc09,123d,80skf";
        Stream<String> words = Pattern.compile("[\\P{L}]+").splitAsStream(content);
        Stream<String> l = words.filter(Pattern.compile("[A-Z]{2,}").asPredicate());
        l.forEach(System.out::print);
    }
    
    @Test
    public void test3(){
        Long a = 121L;
        Long b = 121L;
        System.out.println(Long.compare(a, b));
        System.out.println(b.equals(a));
        System.out.println(Objects.equals(a, b));
        System.out.println(a == b);
    }
    
    @Test
    public void test4(){
        //用于替代System.out.print;
        Logger.getGlobal().info("hello world");
    }
        
}
