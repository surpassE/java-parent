package com.sirding.service.impl;

import com.sirding.service.ServiceA;
import com.sirding.service.ServiceB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceAImpl implements ServiceA {

	@Autowired
	private ServiceB serviceB;
	
	@Override
	public void show() {
//		this.serviceB.show();
		System.out.println("I am serviceA!");
	}

}
