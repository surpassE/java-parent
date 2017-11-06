package com.sirding.javase;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.CLASS)
@interface SampleAnnotation {String occupation() default "";}

@Retention(RetentionPolicy.RUNTIME)
@interface SampleAnnotation2{ String occepation();}

@Retention(RetentionPolicy.SOURCE)
@interface SampleAnnotation3{ String occepation();}

public class Annotate {
	public static void main(String[] args) {
		@SampleAnnotation(occupation = "Carpenter")
		String John;
		
		@SampleAnnotation2(occepation = "software Engineer")
		String Sally;
		
		@SampleAnnotation(occupation = "Maliwoman")
		String Alice = "";
		System.out.println("okok...");
	}
	
}
