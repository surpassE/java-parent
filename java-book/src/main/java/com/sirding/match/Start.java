package com.sirding.match;


public class Start {

	public static void main(String[] args) {
		MatchService match = new MatchService();
//		match.initGoods();
		System.out.println("======初始化背包=======");
		match.initPack();
	}
}