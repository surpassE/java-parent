package com.sirding.service;

import com.framework.redis.annotation.DistributeLock;

public interface LockService {

	@DistributeLock(key = "tli", expression = false)
	void testLock();
}
