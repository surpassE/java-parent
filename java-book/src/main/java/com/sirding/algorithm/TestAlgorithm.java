package com.sirding.algorithm;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * TODO
 *
 * @author zc.ding
 * @create 2018/12/24
 */
public class TestAlgorithm {


    @Test
    public void test1() {
        String s = "abcabcefb";
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int ans = 0, i =0, j = 0;
        while (i < n && j < n) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            }else{
                Character c = s.charAt(i++);
                System.out.println(c);
                set.remove(c);
            }
        }
        System.out.println("长度：" + ans);
        System.out.println(set);
        System.out.println(Integer.MAX_VALUE);
    }
}
