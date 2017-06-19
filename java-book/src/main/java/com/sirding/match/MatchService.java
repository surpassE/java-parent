package com.sirding.match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatchService {

	
	/**
	 * @Described			: 初始化背包容量
	 * @author				: zc.ding
	 * @date 				: 2016年12月26日
	 * @return
	 */
	public List<Pack> initPack(){
		List<Pack> packList = new ArrayList<Pack>();
		packList.add(new Pack(1000, 3, 0.0005));
		packList.add(new Pack(1000, 3, 0.0005));
		packList.add(new Pack(900, 3, 0.0005));
		Collections.sort(packList);
		for(Pack pack : packList){
			System.out.println("包容量：" + pack.getCapacity());
		}
		return packList;
	}
}
