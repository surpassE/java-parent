package com.sirding.service.impl;

import com.sirding.service.ServiceA;
import com.sirding.service.ServiceB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceBImpl implements ServiceB{

	@Autowired
	private ServiceA serviceA;
	
	@Override
	public void show() {
//		this.serviceA.show();
		System.out.println("I am serviceB!");
	}

}