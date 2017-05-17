package com.sirding.zoo;

import org.junit.Test;

public class TestZooLock {

	@Test
	public void tryLock(){
		try {
			ZooFactory.init();
			Thread.sleep(60000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
