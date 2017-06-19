package com.sirding.spring.rmi;

public class RmiServiceImpl implements RmiService{

	@Override
	public String getMsg(String name) {
		return "hello " + name;
	}

}
