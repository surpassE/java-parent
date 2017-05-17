package com.sirding.javase;

import org.apache.commons.lang.StringUtils;

public class TestStringUtils {

	public static void main(String[] args) {
		String tmp = "";
		System.out.println(StringUtils.isNotEmpty(tmp));
		System.out.println(StringUtils.isNotBlank(tmp));
	}
}
