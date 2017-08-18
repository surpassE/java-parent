package com.sirding;

import java.text.ParseException;
import java.util.Date;

public class Demo {

	public static void main(String[] args) throws ParseException {
		System.out.println(DateUtil.convertDateToString(new Date()));
		System.out.println(DateUtil.getDateDownOnMonth("2016-06-15"));
		Integer a = new Integer(1);
		Integer b = new Integer(1);
		System.out.println((a == b));
		String s = new String("11");
		System.out.println((s == "11"));

	}
}
