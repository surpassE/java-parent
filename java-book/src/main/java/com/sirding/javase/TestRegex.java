package com.sirding.javase;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegex {

	public static void main(String[] args) {
		String msg = "{args[0]}对用户{ args[0].id}执行{args[1].state == 1 ? '禁用' : 'jiejin'}操作";
		Pattern p = Pattern.compile("([\\{ *args\\[\\d]+\\][^\\}]* *\\})");
        Matcher m = p.matcher(msg);
//        System.out.println(m.find());
//        System.out.println(m.start());
//        System.out.println(m.group());
        
        while(m.find()) {
        	System.out.println(m.group());
        }
        
        msg = "{args[1].state >= 1 ? '禁用' : 'jiejin'}";
        String[] arr = msg.split("[=<>]+");
        Arrays.asList(arr).forEach(e -> System.out.println(e));
	}
}
