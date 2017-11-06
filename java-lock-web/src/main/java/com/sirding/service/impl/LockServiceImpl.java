package com.sirding.service.impl;

import org.springframework.stereotype.Service;

import com.framework.redis.annotation.DistributeLock;
import com.sirding.service.LockService;

@Service
public class LockServiceImpl implements LockService {

	@Override
	@DistributeLock(key = "tlimpl", expression = false)
	public void testLock() {
		System.out.println("testLock");
	}

}
