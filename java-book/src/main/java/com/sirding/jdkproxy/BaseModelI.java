package com.sirding.jdkproxy;

public interface BaseModelI {

	default String getColumnName() {
		return "hello";
	}
}
